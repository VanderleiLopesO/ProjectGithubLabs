package com.lopessoft.projectgithublabs.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.lopessoft.projectgithublabs.domain.*
import com.lopessoft.projectgithublabs.domain.entities.*
import com.lopessoft.projectgithublabs.infrastructure.usecases.BrowserUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    application: Application,
    val state: SavedStateHandle,
    private val browserUseCase: BrowserUseCase,
    private val scheduler: Scheduler = Schedulers.io()
) : BaseViewModel(application) {

    private val _status = state.getLiveData<RequestStatus>(
        STATUS_LIVE_DATA,
        None
    )
    private val _data = state.getLiveData<Repository?>(DATA_LIVE_DATA, null)
    private val _nextPageData = state.getLiveData<Repository?>(NEXT_PAGE_DATA_LIVE_DATA, null)
    private val _nextPageStatus = state.getLiveData<RequestStatus>(
        NEXT_PAGE_STATUS_LIVE_DATA,
        None
    )

    val status: LiveData<RequestStatus>
        get() = _status

    val data: LiveData<Repository?>
        get() = _data

    val nextPageData: LiveData<Repository?>
        get() = _nextPageData

    val nextPageStatus: LiveData<RequestStatus>
        get() = _nextPageStatus

    var page: Int = 1

    @SuppressLint("CheckResult")
    private fun startRequest(isFirstPage: Boolean) {
        val data = if (isFirstPage) _data else _nextPageData
        val status = if (isFirstPage) _status else _nextPageStatus

        status.postValue(Loading)

        browserUseCase.getRepositories(page, Constants.JAVA, Constants.STARS)
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.value = it
                status.postValue(Loaded)
            }, {
                data.value = null
                status.postValue(
                    Error
                )
            })
    }

    fun requestData() {
        if (data.value != null && status.value != None) {
            return
        }

        startRequest(true)
    }

    fun requestNextPageData() {
        page++
        startRequest(false)
    }

    fun retryRequest(isFirstPage: Boolean) {
        if (isFirstPage) {
            requestData()
        } else {
            startRequest(isFirstPage)
        }
    }

    override fun saveViewModelState() {
        state.apply {
            set(STATUS_LIVE_DATA, _status.value)
            set(DATA_LIVE_DATA, _data.value)
            set(NEXT_PAGE_DATA_LIVE_DATA, _nextPageData.value)
            set(NEXT_PAGE_STATUS_LIVE_DATA, _nextPageStatus.value)
        }
    }

    companion object {
        const val STATUS_LIVE_DATA = "MainViewModel.STATUS_LIVE_DATA"
        const val DATA_LIVE_DATA = "MainViewModel.DATA_LIVE_DATA"
        const val NEXT_PAGE_DATA_LIVE_DATA = "MainViewModel.NEXT_PAGE_DATA_LIVE_DATA"
        const val NEXT_PAGE_STATUS_LIVE_DATA = "MainViewModel.NEXT_PAGE_STATUS_LIVE_DATA"
    }

}