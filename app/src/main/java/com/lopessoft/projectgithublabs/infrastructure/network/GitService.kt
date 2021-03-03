package com.lopessoft.projectgithublabs.infrastructure.network

class GitService {
    val retrofitInterface: GitHubApi = ApiBuilder.retrofit.create(
        GitHubApi::class.java)
}