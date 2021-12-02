package com.reddit.rickandmortyapp.common.mvp

import androidx.annotation.StringRes

interface MvpView {
    fun showErrorMessage(e: Throwable? = null)
    fun showErrorMessage(@StringRes messageRes: Int)
}
