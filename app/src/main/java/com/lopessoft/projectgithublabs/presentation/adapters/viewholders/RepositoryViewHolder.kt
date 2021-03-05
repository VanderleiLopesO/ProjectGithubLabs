package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import com.lopessoft.projectgithublabs.domain.entities.Item
import com.lopessoft.projectgithublabs.presentation.extensions.loadImage
import kotlinx.android.synthetic.main.repository_list_item.view.*
import java.lang.Exception

class RepositoryViewHolder(itemView: View, private val listener: OnItemClickListener?) :
    BaseBrowserViewHolder(itemView) {

    override fun bind(item: Any, position: Int) {
        try {
            val currentItem = (item as Item)

            itemView.run {
                repositoryNameTextView.text = currentItem.name
                repositoryDescriptionTextView.text = currentItem.description
                repositoryForksTextView.text = currentItem.forks.toString()
                repositoryStarsTextView.text = currentItem.starsCount.toString()
                repositoryOwnerLoginTextView.text = currentItem.owner.name

                repositoryUserImage.loadImage(currentItem.owner.image, context)

                setOnClickListener {
                    listener?.onRepositoryClicked(currentItem.owner.name, currentItem.name)
                }
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}