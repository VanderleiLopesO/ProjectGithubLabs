package com.lopessoft.projectgithublabs

import com.lopessoft.projectgithublabs.domain.entities.Owner
import com.lopessoft.projectgithublabs.domain.entities.PullRequestItem
import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository
import com.lopessoft.projectgithublabs.infrastructure.usecases.PullRequestUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest

class PullRequestUseCaseTest: AutoCloseKoinTest() {

    private lateinit var repository: GitRepository

    private lateinit var useCase: PullRequestUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = PullRequestUseCase(repository)
    }

    @Test
    fun testGetRepositories() {
        val pullRequestList =
            listOf(
                PullRequestItem(0, "mocked item", "", "", "", Owner(0, "", ""))
            )

        val name = "fulano"
        val repositoryName = "fulano-repo"

        every { repository.getPullRequests(name, repositoryName) } returns Observable.just(
            pullRequestList
        )

        val result = useCase.getPullRequest("fulano", "fulano-repo")

        result.subscribe {
            Assert.assertEquals(pullRequestList, it)
        }

    }

}