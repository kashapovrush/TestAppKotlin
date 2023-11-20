package com.kashapovrush.godrive.di

import androidx.lifecycle.ViewModel
import com.kashapovrush.godrive.presentation.sign.SignIngViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignIngViewModel::class)
    fun bindSignInViewModel(viewModel: SignIngViewModel): ViewModel
}