package com.lopessoft.projectgithublabs.infrastructure.repositories

import com.lopessoft.projectgithublabs.domain.Constants
import com.lopessoft.projectgithublabs.infrastructure.network.GitService

class GitRepository(private val gitService: GitService) {

    fun getRepositories(
        page: Int,
        language: String = Constants.JAVA,
        sort: String = Constants.STARS
    ) =
        gitService.retrofitInterface.getRepositories(language, sort, page)

    fun getPullRequests(creator: String, repositoryName: String) =
        gitService.retrofitInterface.getPullRequests(creator, repositoryName)

}