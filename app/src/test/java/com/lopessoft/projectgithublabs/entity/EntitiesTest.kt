package com.lopessoft.projectgithublabs.entity

import com.lopessoft.projectgithublabs.domain.entities.*
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.koin.test.AutoCloseKoinTest

class EntitiesTest: AutoCloseKoinTest() {

    @Test
    fun testItem() {
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

        Assert.assertEquals(id, item.id)
        Assert.assertEquals(name, item.name)
        Assert.assertEquals(description, item.description)
        Assert.assertEquals(owner, item.owner)
        Assert.assertEquals(starsCount, item.starsCount)
        Assert.assertEquals(forksCount, item.forks)
        Assert.assertTrue(item is GitHubEntity)
    }

    @Test
    fun testRepositoryItem() {
        val list = listOf(mockk<Item>())

        val item = Repository(list)

        Assert.assertEquals(list, item.items)
    }

    @Test
    fun testLoadingItem() {
        Assert.assertTrue(LoadingItem is GitHubEntity)
    }

    @Test
    fun testErrorItem() {
        Assert.assertTrue(LoadingItem is GitHubEntity)
    }

    @Test
    fun testPullRequestItem() {
        val id = 0L
        val url = "url"
        val title = "title"
        val date = "date"
        val body = "body"
        val ownerId = 0L
        val ownerName = "ownerName"
        val ownerImage = "ownerImage"
        val owner = Owner(ownerId, ownerName, ownerImage)

        val item = PullRequestItem(
            id, url, title, date, body, owner
        )

        Assert.assertEquals(id, item.id)
        Assert.assertEquals(url, item.url)
        Assert.assertEquals(title, item.title)
        Assert.assertEquals(date, item.date)
        Assert.assertEquals(body, item.body)
        Assert.assertEquals(owner, item.owner)
        Assert.assertTrue(item is GitHubEntity)
    }

    @Test
    fun testOwner() {
        val id = 0L
        val name = "name"
        val image = "image"

        val item = Owner(
            id, name, image
        )

        Assert.assertEquals(id, item.id)
        Assert.assertEquals(name, item.name)
        Assert.assertEquals(image, item.image)
    }

}