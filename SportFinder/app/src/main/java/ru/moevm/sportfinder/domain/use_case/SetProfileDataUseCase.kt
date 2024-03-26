package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.ProfileDao
import javax.inject.Inject

class SetProfileDataUseCase @Inject constructor(
    private val profileDao: ProfileDao,
) {

    operator fun invoke(name: String, imageUrl: String): Flow<Boolean> = flow {
        profileDao.setProfileData(name, imageUrl)
        emit(true)
    }
}