package com.reddit.rickandmortyapp.common.base

import android.view.ViewGroup

class ProgressAdapter : BaseAdapter<Boolean>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Boolean> {
        return ProgressViewHolder(parent)
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading && isEmpty()) {
            items.add(true)
            notifyItemInserted(0)
        } else if (!isLoading && isNotEmpty()) {
            items.clear()
            notifyItemRemoved(0)
        }
    }
}
