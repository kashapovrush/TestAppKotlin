package com.kashapovrush.godrive.di

import android.app.Application
import com.kashapovrush.godrive.presentation.mainChat.UserDataActivity
import com.kashapovrush.godrive.presentation.sign.SignInActivity
import com.kashapovrush.godrive.presentation.sign.SignInEnterCodeActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject (application: Application)

    fun inject (activity: SignInActivity)

    fun inject (activity: SignInEnterCodeActivity)

    fun inject(activity: UserDataActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}