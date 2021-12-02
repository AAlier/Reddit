package com.reddit.rickandmortyapp.main.ui

import com.reddit.rickandmortyapp.common.mvp.MvpPresenter
import com.reddit.rickandmortyapp.common.mvp.MvpView
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter

interface MainContract {
    interface View : MvpView {
        fun showLoading(isLoading: Boolean)
        fun showItems(items: List<RickMortyCharacter>)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(isRefresh: Boolean = false)
        fun loadIfNecessary()
    }
}