package com.lopessoft.projectgithublabs.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.Loaded
import com.lopessoft.projectgithublabs.domain.entities.Loading
import com.lopessoft.projectgithublabs.presentation.adapters.MainAdapter
import com.lopessoft.projectgithublabs.presentation.adapters.viewholders.OnItemClickListener
import com.lopessoft.projectgithublabs.presentation.viewmodels.PullRequestViewModel
import kotlinx.android.synthetic.main.activity_pull_request.*
import org.koin.android.viewmodel.ext.android.viewModel

class PullRequestActivity : AppCompatActivity(), OnItemClickListener {

    private val viewModel: PullRequestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        setUpToolbar()
        setUpRecyclerView()
        setUpViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveViewModelState()

        super.onSaveInstanceState(outState)
    }

    private fun setUpToolbar() {
        pullRequestToolbar.apply {
            setTitleTextColor(resources.getColor(android.R.color.white))
            title = (intent.extras?.get(MainActivity.REPOSITORY_NAME) as String)
        }
    }

    private fun setUpRecyclerView() {
        pullRequestRecyclerView.apply {
            val mainAdapter = MainAdapter()
            mainAdapter.listener = this@PullRequestActivity
            adapter = mainAdapter
            setHasFixedSize(true)
            val manager = LinearLayoutManager(this@PullRequestActivity)
            layoutManager = manager
        }
    }

    private fun setUpViewModel() {

        viewModel.run {
            startRequest(
                (intent.extras?.get(MainActivity.USER_NAME) as String),
                (intent.extras?.get(MainActivity.REPOSITORY_NAME) as String)
            )

            status.observe(this@PullRequestActivity, Observer {
                when (it) {
                    Loading -> showProgressBar()
                    Loaded -> showRepositories()
                    else -> showError()
                }
            })

        }
    }

    private fun showProgressBar() {
        pullRequestProgressBar.visibility = View.VISIBLE
        pullRequestRecyclerView.visibility = View.GONE
        pullRequestErrorText.visibility = View.GONE
    }

    private fun showRepositories() {
        viewModel.data.value?.let {

            (pullRequestRecyclerView.adapter as MainAdapter).list = it.toMutableList()

            pullRequestProgressBar.visibility = View.GONE
            pullRequestRecyclerView.visibility = View.VISIBLE
            pullRequestErrorText.visibility = View.GONE
        } ?: kotlin.run {
            showError()
        }
    }

    private fun showError() {
        pullRequestProgressBar.visibility = View.GONE
        pullRequestRecyclerView.visibility = View.GONE
        pullRequestErrorText.visibility = View.VISIBLE
    }

    override fun onRepositoryClicked(userName: String, repositoryName: String) {
        //nothing to do
    }

    override fun onPullRequestClicked(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}