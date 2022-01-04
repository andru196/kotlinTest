package com.example.filmsearch.di

import com.example.filmsearch.data.local.FavoritesDao
import com.example.filmsearch.data.local.FavoritesDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesModule {

    @Binds
    @Singleton
    fun bindFavoritesDao(favoritesDaoImpl: FavoritesDaoImpl): FavoritesDao
}