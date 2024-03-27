package ru.moevm.sportfinder.data.db

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileDaoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ProfileDao {
    private val loginKey = stringPreferencesKey("loginKey")
    private val passwordKey = stringPreferencesKey("passwordKey")
    private val nameKey = stringPreferencesKey("nameKey")
    private val imageUrlKey = stringPreferencesKey("imageUrlKey")
    private val isAutoSignInEnabledKey = booleanPreferencesKey("isAutoSignInEnabledKey")

    override suspend fun getProfileName(): String? {
        return dataStore.data.first()[nameKey]
    }

    override suspend fun getProfileImageUrl(): String? {
        return dataStore.data.first()[imageUrlKey]
    }

    override suspend fun getProfileLogin(): String? {
        return dataStore.data.first()[loginKey]
    }

    override suspend fun setProfileData(name: String, imageUrl: String) {
        dataStore.edit { profileData ->
            profileData[nameKey] = name
            profileData[imageUrlKey] = imageUrl
        }
    }

    override suspend fun isAutoSignInEnabled(): Boolean {
        return dataStore.data.first()[isAutoSignInEnabledKey] == true
    }

    override suspend fun setAutoSignIn(isEnabled: Boolean) {
        dataStore.edit { profileData ->
            profileData[isAutoSignInEnabledKey] = isEnabled
        }
    }

    override suspend fun createProfile(login: String, password: String) {
        clearProfile()
        dataStore.edit { profileData ->
            profileData[loginKey] = login
            profileData[passwordKey] = password
        }
    }

    override suspend fun isProfileExistsFromLoginScreen(
        login: String,
        password: String
    ): Boolean {
        val isLoginExists = dataStore.data.first()[loginKey]?.isNotEmpty() == true
        val isPasswordMatched = (dataStore.data.first()[passwordKey]).let { localPassword ->
            localPassword?.isNotEmpty() == true && localPassword == password
        }
        return isLoginExists && isPasswordMatched
    }

    private suspend fun clearProfile() {
        dataStore.edit { profileData ->
            profileData[loginKey] = ""
            profileData[passwordKey] = ""
            profileData[nameKey] = ""
            profileData[imageUrlKey] = ""
        }
    }
}