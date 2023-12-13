package com.serdar.common.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T : Any, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    private val items = mutableListOf<T>()

    fun updateItems(newItems: List<T>) {
        items.apply {
            clear()
            addAll(newItems)
            notifyDataSetChanged()

        }
    }

    fun getItem(position: Int) = items[position]

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size
}