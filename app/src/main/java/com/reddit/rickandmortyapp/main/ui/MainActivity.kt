package com.reddit.rickandmortyapp.main.ui

import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.reddit.rickandmortyapp.R
import com.reddit.rickandmortyapp.common.base.EndlessDataLoader
import com.reddit.rickandmortyapp.common.base.GridSpacingItemDecoration
import com.reddit.rickandmortyapp.common.base.ProgressAdapter
import com.reddit.rickandmortyapp.common.mvp.BaseMvpActivity
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    override val presenter: MainPresenter by viewModel()
    private val adapter = CharacterAdapter(::onCharacterItemClicked)
    private val progressAdapter = ProgressAdapter()
    private val concatAdapter = ConcatAdapter(adapter, progressAdapter)
    private val pagination by lazy { EndlessDataLoader(recyclerView.layoutManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        presenter.loadData()
    }

    private fun setupRecyclerView() {
        adapter.onItemBindListener = pagination.onItemBindListener
        pagination.onLoadMoreListener = {
            presenter.loadData()
        }

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (progressAdapter.isNotEmpty() && concatAdapter.itemCount == position + 1) 2 else 1
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(GridSpacingItemDecoration(layoutManager.spanCount, 24, true))
        recyclerView.adapter = concatAdapter
    }

    private fun onCharacterItemClicked(item: RickMortyCharacter) {

    }

    override fun showLoading(isLoading: Boolean) {
        recyclerView.post { progressAdapter.setLoading(isLoading) }
    }

    override fun showItems(items: List<RickMortyCharacter>) {
        adapter.setItems(items)
        pagination.reset(items.size)
    }
}