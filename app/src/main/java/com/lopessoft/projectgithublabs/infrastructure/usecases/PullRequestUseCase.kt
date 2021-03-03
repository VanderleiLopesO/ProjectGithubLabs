package com.lopessoft.projectgithublabs.infrastructure

class PullRequestUseCase(private val repository: GitRepository) {

    fun getPullRequest(creator: String, repositoryName: String) =
        repository.getPullRequests(creator, repositoryName)
}