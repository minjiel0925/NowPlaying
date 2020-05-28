package org.example.mjworkspace.myapplication.api

import org.example.mjworkspace.myapplication.BuildConfig
import org.example.mjworkspace.myapplication.fragments.m.model.Now
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNow(
        @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY
    ): Response<ResultsResponse<Now>>
}

const val END_POINT = "https://api.themoviedb.org/3/"
