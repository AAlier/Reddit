package com.reddit.rickandmortyapp.common.mvp

interface MvpPresenter<V : MvpView> {
    fun attach(view: V)
    fun detach()
}
