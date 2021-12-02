package com.reddit.rickandmortyapp.common.di

import org.koin.core.module.Module

interface InjectionModule {
    fun create(): Module
}
