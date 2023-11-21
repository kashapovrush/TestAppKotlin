package com.kashapovrush.godrive.domain.mainChat

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView

interface MainRepository {

    fun changePhotoUser(activity: Activity)

    fun initUserData(view: ImageView, text: TextView)

}