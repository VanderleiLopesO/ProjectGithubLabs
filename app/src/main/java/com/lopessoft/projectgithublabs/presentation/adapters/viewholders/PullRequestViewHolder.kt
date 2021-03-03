package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import com.lopessoft.projectgithublabs.domain.Item
import com.lopessoft.projectgithublabs.infrastructure.extensions.loadImage
import kotlinx.android.synthetic.main.repository_list_item.view.*
import java.lang.Exception

class RepositoryViewHolder(itemView: View, private val listener: OnItemClickListener?) :
    BaseBrowserViewHolder(itemView) {

    override fun bind(item: Any, position: Int) {
        try {
            val currentItem = (item as Item)

            itemView.run {
                repositoryNameTextView.text = currentItem.name
                pullRequestDescriptionTextView.text = currentItem.description
                pullRequestForksTextView.text = currentItem.forks.toString()
                pullRequestStarsTextView.text = currentItem.starsCount.toString()
                pullRequestOwnerLoginTextView.text = currentItem.owner.name

                pullRequestUserImage.loadImage(currentItem.owner.image, context)

                setOnClickListener {
                    listener?.onRepositoryClicked()
                }
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}