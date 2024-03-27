package ru.moevm.sportfinder.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.common.Constants.SPORT_COURTS_API_URL
import ru.moevm.sportfinder.common.userProfileDataStore
import ru.moevm.sportfinder.data.controller.WeatherControllerImpl
import ru.moevm.sportfinder.data.db.ProfileDao
import ru.moevm.sportfinder.data.db.ProfileDaoImpl
import ru.moevm.sportfinder.data.db.room.RunningDao
import ru.moevm.sportfinder.data.db.room.SportFinderDatabase
import ru.moevm.sportfinder.data.db.room.TrainingDao
import ru.moevm.sportfinder.data.remote.ServerApi
import ru.moevm.sportfinder.domain.controller.WeatherController
import ru.moevm.sportfinder.domain.sharing.SharingTextGenerator
import ru.moevm.sportfinder.domain.sharing.SharingTextGeneratorImpl
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesUserProfileDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userProfileDataStore

    @Provides
    @Singleton
    fun provideProfileDao(
        datastore: DataStore<Preferences>
    ): ProfileDao = ProfileDaoImpl(datastore)

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient().newBuilder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)

    @Provides
    @Singleton
    fun provideServerApi(retrofitBuilder: Retrofit.Builder): ServerApi = retrofitBuilder
        .baseUrl(SPORT_COURTS_API_URL)
        .build()
        .create(ServerApi::class.java)

    @Provides
    @Singleton
    fun provideSportFinderDatabase(@ApplicationContext appContext: Context): SportFinderDatabase {
        return Room.databaseBuilder(
            appContext,
            SportFinderDatabase::class.java,
            Constants.SPORT_FINDER_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRunningDao(database: SportFinderDatabase): RunningDao {
        return database.runningDao()
    }

    @Provides
    @Singleton
    fun provideTrainingDao(database: SportFinderDatabase): TrainingDao {
        return database.trainingDao()
    }

    @Provides
    @Singleton
    fun provideSharingTextGenerator(): SharingTextGenerator {
        return SharingTextGeneratorImpl()
    }

    @Provides
    @Singleton
    fun provideWeatherController(): WeatherController {
        return WeatherControllerImpl()
    }
}