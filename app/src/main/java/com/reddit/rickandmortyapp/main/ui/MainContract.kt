package com.reddit.rickandmortyapp.main.ui

import com.reddit.rickandmortyapp.common.mvp.MvpPresenter
import com.reddit.rickandmortyapp.common.mvp.MvpView
import com.reddit.rickandmortyapp.main.api.RickAndMortyCharacter

interface MainContract {
    interface View : MvpView {
        fun showLoading(isLoading: Boolean)
        fun showItems(items: List<RickAndMortyCharacter>)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(isRefresh: Boolean = false)
    }
}