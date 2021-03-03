package com.lopessoft.projectgithublabs.infrastructure.usecases

import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository

class BrowserUseCase (private val repository: GitRepository) {

    fun getRepositories(page:Int, language: String, sort: String) =
        repository.getRepositories(page, language, sort)
}