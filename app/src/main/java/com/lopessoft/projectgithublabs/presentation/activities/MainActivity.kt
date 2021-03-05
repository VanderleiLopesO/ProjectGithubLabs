package com.lopessoft.projectgithublabs.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.*
import com.lopessoft.projectgithublabs.presentation.adapters.MainAdapter
import com.lopessoft.projectgithublabs.presentation.adapters.viewholders.OnItemClickListener
import com.lopessoft.projectgithublabs.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        setUpViewModel()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveViewModelState()

        super.onSaveInstanceState(outState)
    }

    private fun setUpRecyclerView() {
        repositoriesRecyclerView.apply {
            val mainAdapter = MainAdapter()
            mainAdapter.listener = this@MainActivity
            adapter = mainAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                        val totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                        val pastVisibleItems =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            if ((viewModel.page < viewModel.data.value?.items?.size!!) &&
                                viewModel.nextPageStatus.value != Loading) {
                                viewModel.requestNextPageData()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setUpViewModel() {

        viewModel.run {
            requestData()

            status.observe(this@MainActivity, Observer {
                when (it) {
                    Loading -> showProgressBar()
                    Loaded -> showRepositories()
                    else -> showError()
                }
            })

            nextPageData.observe(this@MainActivity, Observer { repository ->
                val tempList = (repositoriesRecyclerView.adapter as MainAdapter).list

                repository?.items?.forEach {
                    tempList.add(it)
                    data.value?.items?.plus(it)
                    (repositoriesRecyclerView.adapter as MainAdapter).apply {
                        list = tempList
                        notifyDataSetChanged()
                    }
                }
            })

            nextPageStatus.observe(this@MainActivity, Observer {
                when (it) {
                    Loading -> showPaginationLoading()
                    Loaded -> hidePaginationLoading()
                    else -> showPaginationError()
                }
            })

        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        repositoriesRecyclerView.visibility = View.GONE
        errorText.visibility = View.GONE
    }

    private fun showPaginationLoading() {
        (repositoriesRecyclerView.adapter as MainAdapter).apply {
            list.add(LoadingItem)
            notifyDataSetChanged()
        }
    }

    private fun hidePaginationLoading() {
        (repositoriesRecyclerView.adapter as MainAdapter).apply {
            list.remove(LoadingItem)
            notifyDataSetChanged()
        }
    }

    private fun showPaginationError() {
        hidePaginationLoading()

        (repositoriesRecyclerView.adapter as MainAdapter).apply {
            list.add(ErrorItem)
            notifyDataSetChanged()
        }
    }

    private fun hidePaginationError() {
        (repositoriesRecyclerView.adapter as MainAdapter).apply {
            list.remove(ErrorItem)
            notifyDataSetChanged()
        }
    }

    private fun showRepositories() {
        viewModel.data.value?.let {
            if (viewModel.page == 1) {
                (repositoriesRecyclerView.adapter as MainAdapter).list = it.items.toMutableList()
            }

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

        errorText.setOnClickListener {
            viewModel.retryRequest(true)
        }
    }

    override fun onRepositoryClicked(userName: String, repositoryName: String) {
        startActivity(Intent(this, PullRequestActivity::class.java).apply {
            putExtra(USER_NAME, userName)
            putExtra(REPOSITORY_NAME, repositoryName)
        })
    }

    override fun onPullRequestClicked(url: String) {
        //nothing to do
    }

    override fun onRetryRequestClicked() {
        hidePaginationError()

        viewModel.retryRequest(false)
    }

    companion object {
        const val USER_NAME = "MainActivity.USER_NAME"
        const val REPOSITORY_NAME = "MainActivity.REPOSITORY_NAME"
    }
}