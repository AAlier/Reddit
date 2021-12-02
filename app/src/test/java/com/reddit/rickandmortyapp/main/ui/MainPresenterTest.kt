package com.reddit.rickandmortyapp.main.ui

import com.reddit.rickandmortyapp.main.api.MainApi
import com.reddit.rickandmortyapp.main.api.MainRepository
import com.reddit.rickandmortyapp.main.api.RickAndMortyCharactersResponse
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class MainPresenterTest : TestCase() {

    @Mock
    private lateinit var presenter: MainContract.Presenter

    @Mock
    private lateinit var view: MainContract.View

    @Mock
    private lateinit var api: MainApi

    @Mock
    private lateinit var repository: MainRepository

    @Test
    fun testLoadDataSuccess() = runBlocking {
        val item = mock(RickAndMortyCharactersResponse::class.java)
        `when`(api.fetchCharactersCoroutine(1)).thenReturn(item)

        presenter.loadData()

        verify(view.showLoading(true))
        verify(view).showItems(item.results)
        verify(view.showLoading(false))
    }

    @Test
    fun testLoadDataError() = runBlocking {
        val exception = mock(IllegalStateException::class.java)
        `when`(api.fetchCharactersCoroutine(1)).thenThrow(exception)
        presenter.loadData()

        verify(view.showLoading(true))
        verify(view).showErrorMessage(exception)
        verify(view.showLoading(false))
    }
}