package com.lopessoft.projectgithublabs.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.infrastructure.BrowserUseCaseImpl
import com.lopessoft.projectgithublabs.infrastructure.GitRepository
import com.lopessoft.projectgithublabs.infrastructure.GitService
import com.lopessoft.projectgithublabs.presentation.adapters.MainAdapter
import com.lopessoft.projectgithublabs.presentation.viewmodels.Loaded
import com.lopessoft.projectgithublabs.presentation.viewmodels.Loading
import com.lopessoft.projectgithublabs.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        setUpViewModel()
    }

    private fun setUpRecyclerView() {
        repositoriesRecyclerView.apply {
            adapter = MainAdapter()
            setHasFixedSize(true)
            val manager = LinearLayoutManager(this@MainActivity)
            layoutManager = manager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        //viewModel.downloadMoreItems()
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }

    private fun setUpViewModel() {
        viewModel =
            MainViewModel(
                application,
                BrowserUseCaseImpl(GitRepository(GitService()))
            )

        viewModel.run {
            startRequest()

            status.observe(this@MainActivity, Observer {
                when (it) {
                    Loading -> showProgressBar()
                    Loaded -> showRepositories()
                    else -> showError()
                }
            })
            
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        repositoriesRecyclerView.visibility = View.GONE
        errorText.visibility = View.GONE
    }

    private fun showRepositories() {
        viewModel.data.value?.let {
            (repositoriesRecyclerView.adapter as MainAdapter).list = it.items.toMutableList()

            progressBar.visibility = View.GONE
            repositoriesRecyclerView.visibility = View.VISIBLE
            errorText.visibility = View.GONE
        } ?: kotlin.run {
            showError()
        }
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        repositoriesRecyclerView.visibility = View.GONE
        errorText.visibility = View.VISIBLE
    }
}