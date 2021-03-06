package com.reddit.rickandmortyapp.common.mvp

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.reddit.rickandmortyapp.common.extensions.showInfoDialog

abstract class BaseMvpActivity<V : MvpView, P : MvpPresenter<V>> : AppCompatActivity(), MvpView {

    abstract val presenter: P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attach(this as V)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showErrorMessage(messageRes: Int) {
        showInfoDialog(messageRes)
    }

    override fun showErrorMessage(e: Throwable?) {
        showInfoDialog(e)
    }
}
