package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.ProfileDao
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
) {

    operator fun invoke(login: String, password: String): Flow<Boolean> = flow {
        profileDao.createProfile(login, password)
        emit(true)
    }
}