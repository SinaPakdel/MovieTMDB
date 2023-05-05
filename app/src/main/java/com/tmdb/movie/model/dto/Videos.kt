package com.tmdb.movie.model.dto


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<ResultX>?
)