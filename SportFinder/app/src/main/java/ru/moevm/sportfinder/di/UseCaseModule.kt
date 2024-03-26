package ru.moevm.sportfinder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.moevm.sportfinder.data.db.ProfileDao
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import ru.moevm.sportfinder.domain.use_case.CreateProfileUseCase
import ru.moevm.sportfinder.domain.use_case.GetProfileImageUrlUseCase
import ru.moevm.sportfinder.domain.use_case.GetProfileNameUseCase
import ru.moevm.sportfinder.domain.use_case.GetSportCourtsUseCase
import ru.moevm.sportfinder.domain.use_case.IsAutoSignInEnabledUseCase
import ru.moevm.sportfinder.domain.use_case.IsProfileExistsFromLoginScreenUseCase
import ru.moevm.sportfinder.domain.use_case.SetAutoSignInUseCase
import ru.moevm.sportfinder.domain.use_case.SetProfileDataUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetSportCourtsUseCase(sportCourtsRepository: SportCourtsRepository) =
        GetSportCourtsUseCase(sportCourtsRepository)

    @Provides
    fun provideGetProfileNameUseCase(profileDao: ProfileDao) =
        GetProfileNameUseCase(profileDao)

    @Provides
    fun provideGetProfileImageUrlUseCase(profileDao: ProfileDao) =
        GetProfileImageUrlUseCase(profileDao)

    @Provides
    fun provideSetProfileDataUseCase(profileDao: ProfileDao) =
        SetProfileDataUseCase(profileDao)

    @Provides
    fun provideCreateProfileUseCase(profileDao: ProfileDao) =
        CreateProfileUseCase(profileDao)

    @Provides
    fun provideIsProfileExistsFromLoginScreenUseCase(profileDao: ProfileDao) =
        IsProfileExistsFromLoginScreenUseCase(profileDao)

    @Provides
    fun provideIsAutoSignInEnabledUseCase(profileDao: ProfileDao) =
        IsAutoSignInEnabledUseCase(profileDao)

    @Provides
    fun provideSetAutoSignInUseCase(profileDao: ProfileDao) =
        SetAutoSignInUseCase(profileDao)
}