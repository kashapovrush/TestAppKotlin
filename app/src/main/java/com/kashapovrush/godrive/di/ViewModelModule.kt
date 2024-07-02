package com.kashapovrush.godrive.di

import androidx.lifecycle.ViewModel
import com.kashapovrush.enter_code_feature.viewmodel.EnterCodeViewModel
import com.kashapovrush.godrive.presentation.mainChat.UserDataViewModel
import com.kashapovrush.godrive.presentation.sign.SignInEnterCodeViewModel
import com.kashapovrush.godrive.presentation.sign.SignIngViewModel
import com.kashapovrush.profile_feature.viewmodel.ProfileViewModel
import com.kashapovrush.settings_feature.viewmodel.SettingsViewModel
import com.kashapovrush.sign_up_feature.viewmodel.SignUpViewModel
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

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnterCodeViewModel::class)
    fun bindEnterCodeViewModel(viewModel: EnterCodeViewModel): ViewModel
}