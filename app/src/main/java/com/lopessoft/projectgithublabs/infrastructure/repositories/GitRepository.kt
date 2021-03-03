package com.lopessoft.projectgithublabs.infrastructure

import com.lopessoft.projectgithublabs.domain.Constants

class GitRepository(private val gitService: GitService) {

    fun getRepositories(page: Int, language: String = Constants.JAVA, sort: String = Constants.STARS) =
        gitService.retrofitInterface.getRepositories(language, sort, page)

    fun getPullRequests(creator: String, repositoryName: String) =
        gitService.retrofitInterface.getPullRequests(creator, repositoryName)

}