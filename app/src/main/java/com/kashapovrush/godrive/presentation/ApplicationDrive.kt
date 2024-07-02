package com.kashapovrush.godrive.presentation

import android.app.Application
import com.kashapovrush.godrive.di.DaggerApplicationComponent

class ApplicationDrive: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


}