package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import com.lopessoft.projectgithublabs.domain.Item
import kotlinx.android.synthetic.main.repository_list_item.view.*
import java.lang.Exception

class RepositoryViewHolder(itemView: View) : BaseBrowserViewHolder(itemView) {

    override fun bind(item: Any, position: Int) {
        try {
            val currentItem = (item as Item)

            itemView.run {
                repositoryNameTextView.text = currentItem.name
                repositoryForksTextView.text = currentItem.forks.toString()
                repositoryStarsTextView.text = currentItem.starsCount.toString()
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}