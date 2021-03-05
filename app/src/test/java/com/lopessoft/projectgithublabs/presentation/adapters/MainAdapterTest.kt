package com.lopessoft.projectgithublabs.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.LoadingItem
import com.lopessoft.projectgithublabs.presentation.adapters.viewholders.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainAdapterTest : AutoCloseKoinTest() {

    private val application = RuntimeEnvironment.application

    private lateinit var adapter: MainAdapter

    @Before
    fun setUp() {
        adapter = MainAdapter()
        adapter.listener = object : OnItemClickListener {
            override fun onRepositoryClicked(userName: String, repositoryName: String) {
                //nothing to do
            }

            override fun onPullRequestClicked(url: String) {
                //nothing to do
            }

            override fun onRetryRequestClicked() {
                //nothing to do
            }
        }
    }

    @Test
    fun testOnCreateViewHolderForRepositoryViewHolder() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.pull_request_list_item, null, false)

        val result = adapter.onCreateViewHolder(view as ViewGroup, MainAdapter.REPOSITORY_TYPE)

        Assert.assertTrue(result is RepositoryViewHolder)
    }

    @Test
    fun testOnCreateViewHolderForPullRequestViewHolder() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.pull_request_list_item, null, false)

        val result = adapter.onCreateViewHolder(view as ViewGroup, MainAdapter.PULL_REQUEST_TYPE)

        Assert.assertTrue(result is PullRequestViewHolder)
    }

    @Test
    fun testOnCreateViewHolderForLoadingViewHolder() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.pull_request_list_item, null, false)

        val result = adapter.onCreateViewHolder(view as ViewGroup, MainAdapter.LOADING_TYPE)

        Assert.assertTrue(result is LoadingViewHolder)
    }

    @Test
    fun testOnCreateViewHolderForErrorViewHolder() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.pull_request_list_item, null, false)

        val result = adapter.onCreateViewHolder(view as ViewGroup, MainAdapter.ERROR_TYPE)

        Assert.assertTrue(result is ErrorViewHolder)
    }

    @Test
    fun testGetItemCount() {
        adapter.list = mutableListOf(LoadingItem)

        Assert.assertEquals(1, adapter.itemCount)
    }

}