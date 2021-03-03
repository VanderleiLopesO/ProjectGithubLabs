package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.PullRequestItem
import com.lopessoft.projectgithublabs.infrastructure.extensions.loadImage
import kotlinx.android.synthetic.main.pull_request_list_item.view.*
import java.lang.Exception

class PullRequestViewHolder(itemView: View, private val listener: OnItemClickListener?) :
    BaseBrowserViewHolder(itemView) {

    override fun bind(item: Any, position: Int) {
        try {
            val currentItem = (item as PullRequestItem)

            itemView.run {
                pullRequestNameTextView.text = currentItem.title
                pullRequestDescriptionTextView.text = currentItem.body
                pullRequestOwnerLoginTextView.text = currentItem.owner.name
                pullRequestCreationDate.text = String.format(
                    context.getString(R.string.pull_request_creation_date_text),
                    currentItem.date
                )

                pullRequestUserImage.loadImage(currentItem.owner.image, context)

                setOnClickListener {
                    listener?.onPullRequestClicked(currentItem.url)
                }
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}