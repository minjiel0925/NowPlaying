package org.example.mjworkspace.myapplication.fragments.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.example.mjworkspace.myapplication.di.CoroutineScopeIO
import org.example.mjworkspace.myapplication.fragments.m.data.now.NowRepository
import javax.inject.Inject


class NowViewModel @Inject constructor(
    private val repository: NowRepository,
    @CoroutineScopeIO
    private val ioCoroutineScope: CoroutineScope
) : ViewModel() {


    var connectivityAvailable: Boolean = false

    val nowList by lazy {
        repository.observePagedNow(
            connectivityAvailable,
            ioCoroutineScope
        )
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}