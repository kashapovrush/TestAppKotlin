package com.kashapovrush.godrive.di

import android.content.Context
import com.kashapovrush.authorization_api.entercode.EnterCodeRepository
import com.kashapovrush.authorization_api.signin.SignInRepository
import com.kashapovrush.authorization_impl.EnterCodeRepositoryImpl
import com.kashapovrush.authorization_impl.SignInRepositoryImpl
import com.kashapovrush.godrive.data.repository.SignRepositoryImpl
import com.kashapovrush.godrive.data.repository.UserDataRepositoryImpl
import com.kashapovrush.godrive.domain.sign.SignRepository
import com.kashapovrush.godrive.domain.userData.UserDataRepository
import com.kashapovrush.notification_api.SettingsRepository
import com.kashapovrush.notification_impl.repository.SettingsRepositoryImpl
import com.kashapovrush.profile_api.ProfileRepository
import com.kashapovrush.profile_impl.ProfileRepositoryImpl
import com.kashapovrush.utils.preferences.PreferenceManagerImpl
import com.kashapovrush.utils.preferences.PreferencesManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindSignInRepository(impl: SignRepositoryImpl): SignRepository

    @ApplicationScope
    @Binds
    fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository

//    @ApplicationScope
//    @Binds
//    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository

    @ApplicationScope
    @Binds
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @ApplicationScope
    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: SignInRepositoryImpl): SignInRepository

    @ApplicationScope
    @Binds
    fun bindEnterCodeRepository(impl: EnterCodeRepositoryImpl): EnterCodeRepository

//    @ApplicationScope
//    @Binds
//    fun provideContext(application: Application): Context

    companion object {

        @Provides
        fun providePreferenceManager(context: Context): PreferencesManager {
            return PreferenceManagerImpl(context)
        }
    }
}