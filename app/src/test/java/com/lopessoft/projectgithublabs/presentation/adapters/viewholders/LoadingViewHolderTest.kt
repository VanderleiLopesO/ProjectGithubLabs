package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.os.Build
import android.view.LayoutInflater
import com.lopessoft.projectgithublabs.BuildConfig
import com.lopessoft.projectgithublabs.R
import kotlinx.android.synthetic.main.error_list_item.view.*
import kotlinx.android.synthetic.main.loading_list_item.view.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LoadingViewHolderTest: AutoCloseKoinTest() {

    private val application = RuntimeEnvironment.application

    @Test
    fun testBind() {
        val view = LayoutInflater.from(application)
            .inflate(R.layout.loading_list_item, null, false)

        val holder = LoadingViewHolder(view)

        holder.bind(0, 0)
        holder.itemView.loadingProgressBar.isEnabled
    }
}