package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.entities.PullRequestItem
import com.lopessoft.projectgithublabs.presentation.extensions.convertToBRTFormatDate
import com.lopessoft.projectgithublabs.presentation.extensions.loadImage
import kotlinx.android.synthetic.main.pull_request_list_item.view.*

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
                    currentItem.date.convertToBRTFormatDate()
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