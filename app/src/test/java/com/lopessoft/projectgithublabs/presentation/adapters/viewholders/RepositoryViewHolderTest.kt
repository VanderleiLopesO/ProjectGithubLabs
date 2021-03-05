package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.os.Build
import android.view.LayoutInflater
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.Item
import com.lopessoft.projectgithublabs.domain.entities.Owner
import com.lopessoft.projectgithublabs.domain.entities.PullRequestItem
import com.lopessoft.projectgithublabs.presentation.activities.PullRequestActivity
import com.lopessoft.projectgithublabs.presentation.extensions.convertToBRTFormatDate
import kotlinx.android.synthetic.main.pull_request_list_item.view.*
import kotlinx.android.synthetic.main.repository_list_item.view.*
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
class RepositoryViewHolderTest : AutoCloseKoinTest() {

    private val application = RuntimeEnvironment.application

    private var callbackCalled = false

    private val listener = object : OnItemClickListener {
        override fun onRepositoryClicked(userName: String, repositoryName: String) {
            callbackCalled = true
        }

        override fun onPullRequestClicked(url: String) {
            //nothing to do
        }

        override fun onRetryRequestClicked() {
            //nothing to do
        }

    }

    @Test
    fun testBind() {
        val id = 0L
        val name = "name"
        val description = "description"
        val ownerId = 0L
        val ownerName = "ownerName"
        val ownerImage = "ownerImage"
        val owner = Owner(ownerId, ownerName, ownerImage)
        val starsCount = 0L
        val forksCount = 0L

        val item = Item(
            id, name, description, owner, starsCount, forksCount
        )

        val view = LayoutInflater.from(application)
            .inflate(R.layout.repository_list_item, null, false)

        val holder = RepositoryViewHolder(view, listener)

        holder.bind(item, 0)

        holder.itemView.run {
            Assert.assertEquals(name, repositoryNameTextView.text)
            Assert.assertEquals(description, repositoryDescriptionTextView.text)
            Assert.assertEquals(forksCount.toString(), repositoryForksTextView.text)
            Assert.assertEquals(starsCount.toString(), repositoryStarsTextView.text)
            Assert.assertEquals(ownerName, repositoryOwnerLoginTextView.text)

            callOnClick()
            Assert.assertTrue(callbackCalled)
        }

    }
}