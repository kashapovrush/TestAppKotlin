package com.kashapovrush.godrive.di

import android.app.Activity
import android.content.Context
import com.kashapovrush.godrive.data.repository.SignRepositoryImpl
import com.kashapovrush.godrive.domain.sign.SignRepository
import com.kashapovrush.godrive.presentation.Application
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindSignInRepository(impl: SignRepositoryImpl): SignRepository

}