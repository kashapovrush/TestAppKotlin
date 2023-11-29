package com.kashapovrush.godrive.di

import com.kashapovrush.godrive.data.repository.UserDataRepositoryImpl
import com.kashapovrush.godrive.data.repository.SignRepositoryImpl
import com.kashapovrush.godrive.domain.userData.UserDataRepository
import com.kashapovrush.godrive.domain.sign.SignRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindSignInRepository(impl: SignRepositoryImpl): SignRepository

    @ApplicationScope
    @Binds
    fun bindMainRepository(impl: UserDataRepositoryImpl): UserDataRepository



}