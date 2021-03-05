package com.lopessoft.projectgithublabs

import com.lopessoft.projectgithublabs.domain.Constants
import com.lopessoft.projectgithublabs.domain.entities.Item
import com.lopessoft.projectgithublabs.domain.entities.Owner
import com.lopessoft.projectgithublabs.domain.entities.PullRequestItem
import com.lopessoft.projectgithublabs.domain.entities.Repository
import com.lopessoft.projectgithublabs.infrastructure.network.GitService
import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GitRepositoryTest {

    private lateinit var service: GitService
    private lateinit var repository: GitRepository

    @Before
    fun setUp() {
        service = mockk()
        repository = GitRepository(service)
    }

    @Test
    fun testGetRepositories() {
        val repositoryItem = Repository(
            listOf(
                Item(0, "mocked item", "", Owner(0, "", ""), 1, 1)
            )
        )
        every {
            service.retrofitInterface.getRepositories(
                Constants.JAVA,
                Constants.STARS,
                1
            )
        } returns Observable.just(repositoryItem)

        val result = repository.getRepositories(1)

        result.subscribe {
            Assert.assertEquals(repositoryItem, it)
        }
    }

    @Test
    fun testGetPullRequests() {
        val pullRequestList =
            listOf(
                PullRequestItem(0, "mocked item", "", "", "", Owner(0, "", ""))
            )

        val name = "fulano"
        val repositoryName = "fulano-repo"

        every {
            service.retrofitInterface.getPullRequests(
                name,
                repositoryName
            )
        } returns Observable.just(
            pullRequestList
        )


        val result = repository.getPullRequests(name, repositoryName)

        result.subscribe {
            Assert.assertEquals(pullRequestList, it)
        }
    }

}