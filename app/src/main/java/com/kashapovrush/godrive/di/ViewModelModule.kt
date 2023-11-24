package com.kashapovrush.godrive.di

import androidx.lifecycle.ViewModel
import com.kashapovrush.godrive.presentation.mainChat.MainViewModel
import com.kashapovrush.godrive.presentation.mainChat.UserDataViewModel
import com.kashapovrush.godrive.presentation.sign.SignInEnterCodeViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SignInEnterCodeViewModel::class)
    fun bindSignInEnterCodeViewModel(viewModel: SignInEnterCodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDataViewModel::class)
    fun bindUserDataViewModel(viewModel: UserDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}