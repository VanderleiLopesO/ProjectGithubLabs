package com.lopessoft.projectgithublabs

import com.lopessoft.projectgithublabs.domain.Constants
import com.lopessoft.projectgithublabs.domain.entities.Item
import com.lopessoft.projectgithublabs.domain.entities.Owner
import com.lopessoft.projectgithublabs.domain.entities.Repository
import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository
import com.lopessoft.projectgithublabs.infrastructure.usecases.BrowserUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BrowserUseCaseTest {

    private lateinit var repository: GitRepository

    private lateinit var useCase: BrowserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = BrowserUseCase(repository)
    }

    @Test
    fun testGetRepositories() {
        val repositoryItem = Repository(
            listOf(
                Item(0, "mocked item", "", Owner(0, "", ""), 1, 1)
            )
        )

        every { repository.getRepositories(1) } returns Observable.just(repositoryItem)

        val result = useCase.getRepositories(1, Constants.JAVA, Constants.STARS)

        result.subscribe {
            Assert.assertEquals(repositoryItem, it)
        }

    }

}