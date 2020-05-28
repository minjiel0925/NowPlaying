package org.example.mjworkspace.myapplication.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultsResponse<T>(
    @Json(name = "results")
    val results: List<T>
)