package ru.moevm.sportfinder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.moevm.sportfinder.data.repository.SportCourtsRepositoryImpl
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSportCourtsRepository(impl: SportCourtsRepositoryImpl): SportCourtsRepository
}