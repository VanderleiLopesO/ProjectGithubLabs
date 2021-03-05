package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.os.Build
import android.view.LayoutInflater
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.Owner
import com.lopessoft.projectgithublabs.domain.entities.PullRequestItem
import com.lopessoft.projectgithublabs.presentation.activities.PullRequestActivity
import com.lopessoft.projectgithublabs.presentation.extensions.convertToBRTFormatDate
import kotlinx.android.synthetic.main.pull_request_list_item.view.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PullRequestViewHolderTest : AutoCloseKoinTest() {

    private val application = RuntimeEnvironment.application

    private var callbackCalled = false

    private val listener = object : OnItemClickListener {
        override fun onRepositoryClicked(userName: String, repositoryName: String) {
            //nothing to do
        }

        override fun onPullRequestClicked(url: String) {
            callbackCalled = true
        }

        override fun onRetryRequestClicked() {
            //nothing to do
        }

    }

    @Test
    fun testBind() {
        val id = 0L
        val url = "url"
        val title = "title"
        val date = "2021-02-14T02:03:25Z"
        val body = "body"
        val ownerId = 0L
        val ownerName = "ownerName"
        val ownerImage = "ownerImage"
        val owner = Owner(ownerId, ownerName, ownerImage)

        val item = PullRequestItem(
            id, url, title, date, body, owner
        )

        val activity = Robolectric.buildActivity(PullRequestActivity::class.java)

        val view = LayoutInflater.from(application)
            .inflate(R.layout.pull_request_list_item, null, false)

        val holder = PullRequestViewHolder(view, listener)

        holder.bind(item, 0)

        holder.itemView.run {
            Assert.assertEquals(title, pullRequestNameTextView)
            Assert.assertEquals(body, pullRequestDescriptionTextView)
            Assert.assertEquals(ownerName, pullRequestOwnerLoginTextView)
            Assert.assertEquals(date.convertToBRTFormatDate(), pullRequestCreationDate)

            callOnClick()
            Assert.assertTrue(callbackCalled)
        }

    }
}