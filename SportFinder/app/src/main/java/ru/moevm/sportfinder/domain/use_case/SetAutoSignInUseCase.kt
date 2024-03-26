package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.ProfileDao
import javax.inject.Inject

class SetAutoSignInUseCase @Inject constructor(
    private val profileDao: ProfileDao
) {

    operator fun invoke(isEnabled: Boolean): Flow<Boolean> = flow {
        profileDao.setAutoSignIn(isEnabled)
        emit(true)
    }
}