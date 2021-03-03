package com.lopessoft.projectgithublabs.infrastructure

class BrowserUseCase (private val repository: GitRepository) {

    fun getRepositories(page:Int, language: String, sort: String) =
        repository.getRepositories(page, language, sort)
}