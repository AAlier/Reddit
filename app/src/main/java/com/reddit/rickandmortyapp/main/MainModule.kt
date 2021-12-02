package com.reddit.rickandmortyapp.main

import com.reddit.rickandmortyapp.common.di.InjectionModule
import com.reddit.rickandmortyapp.main.api.MainApi
import com.reddit.rickandmortyapp.main.api.MainRepository
import com.reddit.rickandmortyapp.main.api.MainRepositoryImpl
import com.reddit.rickandmortyapp.main.ui.MainPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule : InjectionModule {
    override fun create() = module {
        single { get<Retrofit>().create(MainApi::class.java) } bind MainApi::class
        single { MainRepositoryImpl(get()) } bind MainRepository::class

        viewModel { MainPresenter(get()) }
    }

}