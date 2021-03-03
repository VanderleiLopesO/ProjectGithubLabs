package com.lopessoft.projectgithublabs.infrastructure.usecases

import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository

class PullRequestUseCase(private val repository: GitRepository) {

    fun getPullRequest(creator: String, repositoryName: String) =
        repository.getPullRequests(creator, repositoryName)
}