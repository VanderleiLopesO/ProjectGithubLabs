package com.lopessoft.projectgithublabs.infrastructure.di

import androidx.lifecycle.SavedStateHandle
import com.lopessoft.projectgithublabs.infrastructure.usecases.BrowserUseCase
import com.lopessoft.projectgithublabs.infrastructure.repositories.GitRepository
import com.lopessoft.projectgithublabs.infrastructure.network.GitService
import com.lopessoft.projectgithublabs.infrastructure.usecases.PullRequestUseCase
import com.lopessoft.projectgithublabs.presentation.viewmodels.MainViewModel
import com.lopessoft.projectgithublabs.presentation.viewmodels.PullRequestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val module = module {

        factory { SavedStateHandle() }

        single { GitService() }

        single {
            GitRepository(
                get()
            )
        }

        single {
            BrowserUseCase(
                get()
            )
        }
        single {
            PullRequestUseCase(
                get()
            )
        }

        viewModel { PullRequestViewModel(get(), get(), get()) }
        viewModel { MainViewModel(get(), get(), get()) }
    }
}