package com.lopessoft.projectgithublabs.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lopessoft.projectgithublabs.domain.Constants
import com.lopessoft.projectgithublabs.domain.Repository
import com.lopessoft.projectgithublabs.infrastructure.BrowserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

sealed class RequestStatus
object None: RequestStatus()
object Loading: RequestStatus()
object Loaded: RequestStatus()
class Error(throwable: Throwable) : RequestStatus()

class MainViewModel(
    application: Application,
    private val browserUseCase: BrowserUseCase
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<RequestStatus>(
        None
    )
    private val _data = MutableLiveData<Repository?>(null)

    val status: LiveData<RequestStatus>
        get() = _status

    val data: LiveData<Repository?>
        get() = _data

    var page: Int = 1

    fun startRequest() {
        _status.postValue(Loading)

        browserUseCase.getRepositories(page, Constants.JAVA, Constants.STARS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _data.value = it
                _status.postValue(Loaded)
            }, {
                _data.value = null
                _status.postValue(
                    Error(
                        it
                    )
                )
            })
    }

    fun downloadMoreItems() {
        page += 1

        browserUseCase.getRepositories(page, Constants.JAVA, Constants.STARS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _data.value = it
                _status.postValue(Loaded)
            }, {
                _data.value = null
                _status.postValue(
                    Error(
                        it
                    )
                )
            })
    }

}