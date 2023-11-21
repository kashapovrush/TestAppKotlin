package com.kashapovrush.godrive.presentation

import android.app.Application
import com.kashapovrush.godrive.di.DaggerApplicationComponent

class Application: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


}