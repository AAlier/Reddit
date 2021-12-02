package com.reddit.rickandmortyapp.main.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.reddit.rickandmortyapp.R
import com.reddit.rickandmortyapp.common.base.BaseDiffAdapter
import com.reddit.rickandmortyapp.common.base.BaseViewHolder
import com.reddit.rickandmortyapp.common.base.EndlessDataLoader
import com.reddit.rickandmortyapp.common.extensions.setThrottleOnClickListener
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter(
    private val onItemClicked: (RickMortyCharacter) -> Unit
) : BaseDiffAdapter<RickMortyCharacter>() {
    override val mDiffer = AsyncListDiffer(this, diffCallback)
    var onItemBindListener: EndlessDataLoader.OnItemBindListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RickMortyCharacter> {
        return CharacterViewHolder(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RickMortyCharacter>, position: Int) {
        super.onBindViewHolder(holder, position)
        onItemBindListener?.onItemBind(position)
    }
}

private class CharacterViewHolder(
    parent: ViewGroup,
    private val onItemClicked: (RickMortyCharacter) -> Unit
) : BaseViewHolder<RickMortyCharacter>(parent, R.layout.item_character) {
    private val imageView = itemView.imageView
    private val textView = itemView.textView

    override fun onBind(item: RickMortyCharacter) {
        Glide.with(itemView)
            .load(item.image)
            .into(imageView)

        textView.text = item.name

        itemView.setThrottleOnClickListener {
            onItemClicked(item)
        }
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        Glide.with(itemView).clear(imageView)
    }
}

private val diffCallback: DiffUtil.ItemCallback<RickMortyCharacter> =
    object : DiffUtil.ItemCallback<RickMortyCharacter>() {
        override fun areItemsTheSame(
            oldItem: RickMortyCharacter,
            newItem: RickMortyCharacter
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RickMortyCharacter,
            newItem: RickMortyCharacter
        ): Boolean {
            return oldItem == newItem
        }
    }