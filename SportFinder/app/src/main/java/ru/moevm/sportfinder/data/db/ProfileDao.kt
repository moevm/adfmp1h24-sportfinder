package ru.moevm.sportfinder.data.db

interface ProfileDao {

    suspend fun getProfileName(): String?

    suspend fun getProfileImageUrl(): String?

    suspend fun getProfileLogin(): String?

    suspend fun setProfileData(name: String, imageUrl: String)

    suspend fun isAutoSignInEnabled(): Boolean

    suspend fun setAutoSignIn(isEnabled: Boolean)

    suspend fun createProfile(login: String, password: String)

    suspend fun isProfileExistsFromLoginScreen(login: String, password: String): Boolean
}