package org.example.mjworkspace.myapplication.fragments.m.data.now.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import org.example.mjworkspace.myapplication.fragments.m.data.now.NowRemoteDataSource
import org.example.mjworkspace.myapplication.fragments.m.model.Now
import org.example.mjworkspace.myapplication.fragments.m.room.NowDao

class NowPageDataSourceFactory(
    private val dataSource: NowRemoteDataSource,
    private val dao: NowDao,
    private val scope: CoroutineScope
): DataSource.Factory<Int, Now>() {


    private val liveData =
        MutableLiveData<NowPageDataSource>()


    override fun create(): DataSource<Int, Now> {
        val source = NowPageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }


    companion object {
        private val PAGE_SIZE = 100

        fun pagedListConfig() =
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(true)
                .build()
    }
}