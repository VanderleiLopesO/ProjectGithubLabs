package com.lopessoft.projectgithublabs.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopessoft.projectgithublabs.R
import com.lopessoft.projectgithublabs.domain.Item
import com.lopessoft.projectgithublabs.presentation.adapters.viewholders.BaseBrowserViewHolder
import com.lopessoft.projectgithublabs.presentation.adapters.viewholders.RepositoryViewHolder

class MainAdapter : RecyclerView.Adapter<BaseBrowserViewHolder>() {

    var list = mutableListOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBrowserViewHolder =
        RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_list_item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseBrowserViewHolder, position: Int) =
        holder.bind(list[position], position)

}
