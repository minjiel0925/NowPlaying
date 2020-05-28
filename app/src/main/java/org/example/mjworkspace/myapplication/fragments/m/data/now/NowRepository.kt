package org.example.mjworkspace.myapplication.fragments.m.data.now

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import org.example.mjworkspace.myapplication.base.resultLiveData
import org.example.mjworkspace.myapplication.fragments.m.data.now.paging.NowPageDataSourceFactory
import org.example.mjworkspace.myapplication.fragments.m.model.Now
import org.example.mjworkspace.myapplication.fragments.m.room.NowDao
import javax.inject.Inject

class NowRepository @Inject constructor(
    private val nowDao: NowDao,
    private val nowRemoteDataSource: NowRemoteDataSource
) {

    fun observePagedNow(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<Now>> {
        return if (connectivityAvailable)
            observeRemotePagedNow(coroutineScope)
        else
            observeLocalPagedNow()
    }


    private fun observeLocalPagedNow(): LiveData<PagedList<Now>> {
        val dataSourceFactory =
            nowDao.getPagedNow()

        return LivePagedListBuilder(
            dataSourceFactory,
            NowPageDataSourceFactory.pagedListConfig()
        ).build()
    }


    private fun observeRemotePagedNow(
        ioCoroutineScope: CoroutineScope
    ): LiveData<PagedList<Now>> {
        val dataSourceFactory =
            NowPageDataSourceFactory(
                nowRemoteDataSource,
                nowDao,
                ioCoroutineScope
            )

        return LivePagedListBuilder(
            dataSourceFactory,
            NowPageDataSourceFactory.pagedListConfig()
        ).build()
    }


    fun observeNow() = resultLiveData(
        databaseQuery = { nowDao.getNow() },
        networkCall = { nowRemoteDataSource.fetchNow() },
        saveCallResult = { nowDao.insertNow(it.results) }
    )

    companion object {
        const val PAGE_SIZE = 100

        // For Singleton instantiation
        @Volatile
        private var instance: NowRepository? = null

        fun getInstance(
            dao: NowDao,
            remoteDataSource: NowRemoteDataSource
        ) =
            instance
                ?: synchronized(this) {
                instance
                    ?: NowRepository(
                        nowDao = dao,
                        nowRemoteDataSource = remoteDataSource
                    )
                        .also { instance = it }
            }
    }
}