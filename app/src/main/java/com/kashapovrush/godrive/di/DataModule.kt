package com.kashapovrush.godrive.di

import com.kashapovrush.godrive.data.repository.MainRepositoryImpl
import com.kashapovrush.godrive.data.repository.SignRepositoryImpl
import com.kashapovrush.godrive.domain.mainChat.MainRepository
import com.kashapovrush.godrive.domain.models.User
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
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository



}