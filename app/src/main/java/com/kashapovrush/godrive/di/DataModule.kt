package com.kashapovrush.godrive.di

import android.app.Application
import android.content.Context
import com.kashapovrush.godrive.data.repository.MainRepositoryImpl
import com.kashapovrush.godrive.data.repository.UserDataRepositoryImpl
import com.kashapovrush.godrive.data.repository.SignRepositoryImpl
import com.kashapovrush.godrive.domain.mainChat.MainRepository
import com.kashapovrush.godrive.domain.userData.UserDataRepository
import com.kashapovrush.godrive.domain.sign.SignRepository
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

    @ApplicationScope
    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository

    @ApplicationScope
    @Binds
    fun provideContext(application: Application): Context
}