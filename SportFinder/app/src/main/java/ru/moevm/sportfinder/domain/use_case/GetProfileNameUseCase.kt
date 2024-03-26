package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.ProfileDao
import javax.inject.Inject

class GetProfileNameUseCase @Inject constructor(
    private val profileDao: ProfileDao,
) {
    operator fun invoke(): Flow<String?> = flow {
        val name = profileDao.getProfileName()
        emit(name)
    }
}