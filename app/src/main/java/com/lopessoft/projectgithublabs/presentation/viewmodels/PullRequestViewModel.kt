package com.lopessoft.projectgithublabs.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.lopessoft.projectgithublabs.domain.entities.*
import com.lopessoft.projectgithublabs.infrastructure.usecases.PullRequestUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PullRequestViewModel(
    application: Application,
    private val state: SavedStateHandle,
    private val useCase: PullRequestUseCase
) : BaseViewModel(application) {

    private val _status = state.getLiveData<RequestStatus>(
        STATUS_LIVE_DATA,
        None
    )
    private val _data = state.getLiveData<List<PullRequestItem>?>(DATA_LIVE_DATA, null)

    val status: LiveData<RequestStatus>
        get() = _status

    val data: LiveData<List<PullRequestItem>?>
        get() = _data

    private lateinit var creator: String
    private lateinit var repositoryName: String

    @SuppressLint("CheckResult")
    fun startRequest(creator: String, repositoryName: String) {
        this.creator = creator
        this.repositoryName = repositoryName

        if (data.value != null && status.value != None) {
            return
        }

        _status.postValue(Loading)

        useCase.getPullRequest(creator, repositoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _data.value = it
                _status.postValue(Loaded)
            }, {
                _data.value = null
                _status.postValue(
                    Error
                )
            })
    }

    fun retryRequest() {
        startRequest(creator, repositoryName)
    }

    override fun saveViewModelState() {
        state.apply {
            set(STATUS_LIVE_DATA, _status.value)
            set(DATA_LIVE_DATA, _data.value)
        }
    }

    companion object {
        const val STATUS_LIVE_DATA = "PullRequestViewModel.STATUS_LIVE_DATA"
        const val DATA_LIVE_DATA = "PullRequestViewModel.DATA_LIVE_DATA"
    }

}