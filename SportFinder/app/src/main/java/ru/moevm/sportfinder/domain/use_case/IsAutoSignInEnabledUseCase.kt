package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.ProfileDao
import javax.inject.Inject

class IsAutoSignInEnabledUseCase @Inject constructor(
    private val profileDao: ProfileDao
) {

    operator fun invoke(): Flow<Boolean> = flow {
        val isEnabled = profileDao.isAutoSignInEnabled()
        emit(isEnabled)
    }
}