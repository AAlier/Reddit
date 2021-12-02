package com.reddit.rickandmortyapp.main.ui

import com.reddit.rickandmortyapp.common.mvp.BasePresenter
import com.reddit.rickandmortyapp.main.api.MainRepository
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPresenter(
    private val repository: MainRepository
) : BasePresenter<MainContract.View>(), MainContract.Presenter {

    private val data = mutableListOf<RickMortyCharacter>()
    private var isDataFullyLoaded: Boolean = false
    private var page = 1
    private var job: Job? = null

    override fun loadIfNecessary() {
        if (data.isEmpty()) loadData()
        else view?.showItems(data)
    }

    override fun loadData(isRefresh: Boolean) {
        if (isRefresh) {
            page = 1
            isDataFullyLoaded = false
        }
        if (isDataFullyLoaded || job?.isActive == true) return
        job?.cancel()
        job = launch {
            try {
                view?.showLoading(isLoading = true)
                val items = repository.loadCharacters(page)
                if (items.isEmpty()) {
                    isDataFullyLoaded = true
                } else {
                    page++
                }
                if (isRefresh) data.clear()
                data.addAll(items)
                view?.showLoading(isLoading = false)
                view?.showItems(ArrayList(data))
            } catch (e: Exception) {
                if (e is CancellationException) return@launch
                view?.showErrorMessage(e)
                view?.showLoading(isLoading = false)
            }
        }
    }
}