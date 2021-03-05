package com.lopessoft.projectgithublabs.presentation.adapters.viewholders

import android.view.View
import kotlinx.android.synthetic.main.error_list_item.view.*

class ErrorViewHolder(itemView: View, private val listener: OnItemClickListener) :
    BaseBrowserViewHolder(itemView) {

    override fun bind(item: Any, position: Int) {
        itemView.apply {
            errorText.setOnClickListener {
                listener.onRetryRequestClicked()
            }
        }
    }

}