package org.example.mjworkspace.myapplication.fragments.m.data.now

import org.example.mjworkspace.myapplication.api.MovieService
import org.example.mjworkspace.myapplication.base.BaseDataSource

class NowRemoteDataSource(private val service: MovieService) :
     BaseDataSource() {

    suspend fun fetchNow() =
        getResult { service.getNow() }

}