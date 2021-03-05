package com.lopessoft.projectgithublabs.presentation

import android.app.Activity
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lopessoft.projectgithublabs.domain.entities.*
import com.lopessoft.projectgithublabs.infrastructure.usecases.PullRequestUseCase
import com.lopessoft.projectgithublabs.presentation.viewmodels.PullRequestViewModel
import com.lopessoft.projectgithublabs.presentation.viewmodels.PullRequestViewModel.Companion.DATA_LIVE_DATA
import com.lopessoft.projectgithublabs.presentation.viewmodels.PullRequestViewModel.Companion.STATUS_LIVE_DATA
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class PullRequestViewModelTest {

    private lateinit var viewModel: PullRequestViewModel
    private lateinit var savedState: SavedStateHandle
    private lateinit var useCase: PullRequestUseCase
    private var activity: Activity = Robolectric.setupActivity(Activity::class.java)

    private val creator = "fulano"
    private val repositoryName = "fulano-repo"
    private val mockedList = listOf(
        PullRequestItem(0, "", "", "", "", Owner(0, "", ""))
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
            PullRequestViewModel(
                activity.application,
                savedState,
                useCase,
                Schedulers.trampoline()
            )
    }

    @Test
    fun testStartRequest() {
        mockUseCase()

        viewModel.startRequest(creator, repositoryName)

        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(mockedList, viewModel.data.value)
        Assert.assertEquals(Loaded, viewModel.status.value)
    }

    @Test
    fun testStartRequestWithCachedData() {
        mockUseCase()

        viewModel.startRequest(creator, repositoryName)
        shadowOf(Looper.getMainLooper()).idle()
        val result = Pair(
            viewModel.data.value!!,
            viewModel.status.value!!
        )

        viewModel.startRequest(creator, repositoryName)
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(result.first, viewModel.data.value)
        Assert.assertEquals(result.second, viewModel.status.value)
    }

    @Test
    fun testStartRequestOnErrorCase() {
        mockUseCaseForThrowError()

        viewModel.startRequest(creator, repositoryName)

        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertNull(viewModel.data.value)
        Assert.assertEquals(Error, viewModel.status.value)
    }

    @Test
    fun testRetryRequest() {
        mockUseCase()

        viewModel.startRequest(creator, repositoryName)
        shadowOf(Looper.getMainLooper()).idle()
        val result = Pair(
            viewModel.data.value!!,
            viewModel.status.value!!
        )

        viewModel.retryRequest()
        shadowOf(Looper.getMainLooper()).idle()

        Assert.assertEquals(creator, viewModel.getCreator)
        Assert.assertEquals(repositoryName, viewModel.getRepositoryName)
        Assert.assertEquals(result.first, viewModel.data.value)
        Assert.assertEquals(result.second, viewModel.status.value)
    }

    @Test
    fun testSaveViewModelState() {
        mockUseCase()

        viewModel.startRequest(creator, repositoryName)
        shadowOf(Looper.getMainLooper()).idle()

        viewModel.saveViewModelState()

        Assert.assertEquals(viewModel.data.value, viewModel.state[DATA_LIVE_DATA])
        Assert.assertEquals(viewModel.status.value, viewModel.state[STATUS_LIVE_DATA])
    }

    private fun mockUseCase() {
        every {
            useCase.getPullRequest(
                creator,
                repositoryName
            )
        } returns Observable.just(mockedList)
    }

    private fun mockUseCaseForThrowError() {
        every {
            useCase.getPullRequest(
                creator,
                repositoryName
            )
        } returns Observable.error(Throwable())
    }

}