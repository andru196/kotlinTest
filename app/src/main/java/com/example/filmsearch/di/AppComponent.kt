package com.example.filmsearch.di

import com.example.filmsearch.presentation.topFilms.TopFilmsFragment
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(topFilmsFragment: TopFilmsFragment)
}