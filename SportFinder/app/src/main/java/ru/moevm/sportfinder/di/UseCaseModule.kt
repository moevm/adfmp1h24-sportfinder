package ru.moevm.sportfinder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import ru.moevm.sportfinder.domain.use_case.GetSportCourtsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetSportCourtsUseCase(sportCourtsRepository: SportCourtsRepository) =
        GetSportCourtsUseCase(sportCourtsRepository)
}