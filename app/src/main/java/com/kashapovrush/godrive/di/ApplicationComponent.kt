package com.kashapovrush.godrive.di

import android.app.Application
import android.content.Context
//import com.kashapovrush.godrive.presentation.sign.SignInEnterCodeActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: Application)
//
//    fun inject(activity: SignInActivity)

//    fun inject(activity: SignInEnterCodeActivity)
//
//    fun inject(activity: UserDataActivity)

//    fun inject(activity: com.kashapovrush.godrive.presentation.mainChat.MainActivity)

    fun inject(activity: com.kashapovrush.godrive.presentation.MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}