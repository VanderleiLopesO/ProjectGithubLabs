package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onRepositoryClicked(userName: String, repositoryName: String)
    fun onPullRequestClicked(url: String)
    fun onRetryRequestClicked()
}

abstract class BaseBrowserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind(item: Any, position: Int) {
        //nothing to do
    }
}