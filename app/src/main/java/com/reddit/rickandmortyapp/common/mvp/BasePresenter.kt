package com.reddit.rickandmortyapp.common.mvp

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : MvpView> : ViewModel(), MvpPresenter<V>, CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    protected var view: V? = null
        private set

    @CallSuper
    override fun attach(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detach() {
        coroutineContext.cancelChildren()
        view = null
    }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}
