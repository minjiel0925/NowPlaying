package org.example.mjworkspace.myapplication.fragments.m.data.now.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.mjworkspace.myapplication.base.Result
import org.example.mjworkspace.myapplication.fragments.m.data.now.NowRemoteDataSource
import org.example.mjworkspace.myapplication.fragments.m.model.Now
import org.example.mjworkspace.myapplication.fragments.m.room.NowDao
import timber.log.Timber


class NowPageDataSource(
    private val dataSource: NowRemoteDataSource,
    private val dao: NowDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Now>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Now>
    ) {
        fetchData {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Now>
    ) {
        val page = params.key
        fetchData {
            callback.onResult(it, page+1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Now>
    ) {
       val page = params.key
        fetchData {
            callback.onResult(it, page-1)
        }
    }


    private fun fetchData(
        callback: (List<Now>) -> Unit
    ) {
        scope.launch(getJobErrorHandler()) {
            val response =
                dataSource.fetchNow()
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.results
                dao.insertNow(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }


    private fun getJobErrorHandler() =
        CoroutineExceptionHandler { _, throwable ->
            postError(throwable.message ?: throwable.toString())
        }

    private fun postError(message: String) {
        Timber.e("An error occurred: $message")
    }
}