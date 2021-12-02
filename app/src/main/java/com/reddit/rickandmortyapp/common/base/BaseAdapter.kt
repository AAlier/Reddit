package com.reddit.rickandmortyapp.common.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected val items = ArrayList<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.onBind(items[position])

    override fun getItemCount() = items.size

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    fun isEmpty(): Boolean = items.isEmpty()

    fun isNotEmpty(): Boolean = items.isNotEmpty()

    fun clear() {
        setItems(emptyList())
    }

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<T>) {
        val start = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    fun removeItem(element: T) {
        val position = items.indexOf(element)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    protected fun removeItem(predicate: (T) -> Boolean) {
        val position = items.indexOfFirst(predicate)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): T = items[position]
}
