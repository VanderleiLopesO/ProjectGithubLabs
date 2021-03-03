package com.lopessoft.projectgithublabs.infrastructure

class GitService {
    val retrofitInterface: GitHubApi = ApiBuilder.retrofit.create(GitHubApi::class.java)
}