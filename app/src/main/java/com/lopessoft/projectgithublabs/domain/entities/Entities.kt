package com.lopessoft.projectgithublabs.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

sealed class GitHubEntity

data class Item(
    val id: Long,
    val name: String,
    val description: String,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val starsCount: Long,
    val forks: Long
) : GitHubEntity()

data class Repository(
    val items: List<Item>
) : Serializable

object LoadingItem : GitHubEntity()

data class PullRequestItem(
    val id: Long,
    @SerializedName("html_url")
    val url: String,
    val title: String,
    @SerializedName("created_at")
    val date: String,
    val body: String,
    @SerializedName("user")
    val owner: Owner
) : GitHubEntity()

data class Owner(
    val id: Long,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val image: String
)