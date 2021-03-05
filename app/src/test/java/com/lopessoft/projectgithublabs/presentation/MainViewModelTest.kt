package com.lopessoft.projectgithublabs.presentation

import android.app.Activity
import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lopessoft.projectgithublabs.domain.Constants
import com.lopessoft.projectgithublabs.domain.entities.*
import com.lopessoft.projectgithublabs.infrastructure.usecases.BrowserUseCase
import com.lopessoft.projectgithublabs.presentation.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainViewModelTest: AutoCloseKoinTest() {

    private lateinit var viewModel: MainViewModel
    private lateinit var spyViewModel: MainViewModel

    private lateinit var savedState: SavedStateHandle
    private lateinit var useCase: BrowserUseCase
    private var activity: Activity = Robolectric.setupActivity(Activity::class.java)

    private val mockedList = Repository(
        listOf(
            Item(0, "", "", Owner(0, "", ""), 0, 0)
        )
    )

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        savedState = SavedStateHandle()
        useCase = mockk()
        viewModel =
            MainViewModel(
                activity.application,
                savedState,
                useCase,
                Schedulers.trampoline()
            )
        spyViewModel = spyk(viewModel, recordPrivateCalls = true)
    }

    @Test
    fun testRequestData() {
        mockUseCase()

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(mockedList, viewModel.data.value)
        Assert.assertEquals(Loaded, viewModel.status.value)
    }

    @Test
    fun testRequestDataForErrorCase() {
        mockUseCaseForThrowError()

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(null, viewModel.data.value)
        Assert.assertEquals(Error, viewModel.status.value)
    }

    @Test
    fun testRequestDataWithCachedData() {
        mockUseCase()

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()
        val result = Pair(
            viewModel.data.value!!,
            viewModel.status.value!!
        )

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(result.first, viewModel.data.value)
        Assert.assertEquals(result.second, viewModel.status.value)
    }

    @Test
    fun testRequestNextPageData() {
        mockUseCase()

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()

        viewModel.requestNextPageData()
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(mockedList, viewModel.data.value)
        Assert.assertEquals(Loaded, viewModel.status.value)
        Assert.assertEquals(2, viewModel.page)
    }

    @Test
    fun testRetryRequest() {
        mockUseCase()

        spyViewModel.retryRequest(true)
        shadowOf(Looper.getMainLooper()).idle()

        verify { spyViewModel invokeNoArgs "requestData" }
    }

    @Test
    fun testRetryRequestForNextPage() {
        mockUseCase()

        spyViewModel.retryRequest(false)
        shadowOf(Looper.getMainLooper()).idle()

        verify { spyViewModel["startRequest"](false) }
    }

    @Test
    fun testSaveViewModelState() {
        mockUseCase()

        viewModel.requestData()
        shadowOf(Looper.getMainLooper()).idle()

        viewModel.requestNextPageData()
        shadowOf(Looper.getMainLooper()).idle()

        viewModel.saveViewModelState()

        Assert.assertEquals(viewModel.data.value, viewModel.state[MainViewModel.DATA_LIVE_DATA])
        Assert.assertEquals(viewModel.status.value, viewModel.state[MainViewModel.STATUS_LIVE_DATA])
        Assert.assertEquals(
            viewModel.nextPageData.value,
            viewModel.state[MainViewModel.NEXT_PAGE_DATA_LIVE_DATA]
        )
        Assert.assertEquals(
            viewModel.nextPageStatus.value,
            viewModel.state[MainViewModel.NEXT_PAGE_STATUS_LIVE_DATA]
        )
    }

    private fun mockUseCase() {
        every {
            useCase.getRepositories(
                any(),
                Constants.JAVA,
                Constants.STARS
            )
        } returns Observable.just(mockedList)
    }

    private fun mockUseCaseForThrowError() {
        every {
            useCase.getRepositories(
                any(),
                Constants.JAVA,
                Constants.STARS
            )
        } returns Observable.error(Throwable())
    }

}