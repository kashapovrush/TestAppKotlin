package com.kashapovrush.godrive.domain.mainChat

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

interface MainRepository {

    fun changePhotoUser(activity: Activity)

    fun initDataUser(viewImage: ImageView, text: TextView)

}