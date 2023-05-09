package com.tmdb.movie.data.remote.model.details


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<ResultX>?
)