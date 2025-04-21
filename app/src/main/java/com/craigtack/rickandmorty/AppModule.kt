package com.craigtack.rickandmorty

import com.craigtack.rickandmorty.network.CharacterRepository
import com.craigtack.rickandmorty.network.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideCharactersRepository(repository: CharacterRepositoryImpl): CharacterRepository
}
