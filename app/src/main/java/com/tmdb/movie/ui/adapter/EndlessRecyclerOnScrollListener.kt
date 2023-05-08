package com.tmdb.movie.ui.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener(
    private val mLinearLayoutManager: LinearLayoutManager,
    private val pageSize: Int = 5
) : RecyclerView.OnScrollListener() {
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var presentTotalItemCount: Int = 0
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    var visibleThreshold = pageSize // The minimum amount of items to have below your current scroll position before loading more.
    var currentPage = 1
    private var mFullTotalItemCount: Int? = null

    fun refresh() {
        currentPage = 1
        previousTotal = 0
        mFullTotalItemCount = null
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        presentTotalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition()

        if (loading) {
            if (presentTotalItemCount > previousTotal) {
                loading = false
                previousTotal = presentTotalItemCount
            }
        }
        if (!loading && presentTotalItemCount == lastVisibleItem + 1) {
            // End has been reached

            currentPage++
            onLoadMore(currentPage)
            loading = true

//            if (mFullTotalItemCount != null && currentPage * pageSize < mFullTotalItemCount!!) {
//                currentPage++
//
//                onLoadMore(currentPage)
//
//                loading = true
//            }
        }
    }

    abstract fun onLoadMore(currentPage: Int)

    fun setTotalItemCount(count: Int) {
        mFullTotalItemCount = count
    }
}

abstract class EndOfTheListListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private var loading = true
    private var currentPage = 0
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) //check for scroll down
        {
            visibleItemCount = layoutManager.childCount
            totalItemCount = layoutManager.itemCount
            pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loading = false
                    onLoadMore(++currentPage)
                }
            }
        }
    }

    abstract fun onLoadMore(currentPage: Int)
}
