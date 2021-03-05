package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.os.Build
import android.view.LayoutInflater
import com.lopessoft.projectgithublabs.BuildConfig
import com.lopessoft.projectgithublabs.R
import kotlinx.android.synthetic.main.error_list_item.view.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ErrorViewHolderTest: AutoCloseKoinTest() {

    private val application = RuntimeEnvironment.application

    private var callbackCalled = false

    private val listener = object : OnItemClickListener {
        override fun onRepositoryClicked(userName: String, repositoryName: String) {
            //nothing to do
        }

        override fun onPullRequestClicked(url: String) {
            //nothing to do
        }

        override fun onRetryRequestClicked() {
            callbackCalled = true
        }

    }

    @Test
    fun testBind() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.error_list_item, null, false)

        val holder = ErrorViewHolder(view, listener)

        holder.bind(0, 0)
        holder.itemView.errorText.callOnClick()
        Assert.assertTrue(callbackCalled)
    }
}