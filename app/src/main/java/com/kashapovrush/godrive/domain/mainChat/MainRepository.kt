package com.kashapovrush.godrive.domain.mainChat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView

interface MainRepository {

    fun changePhotoUser(activity: Activity)

    fun initUserData(view: ImageView, text: TextView, state: Boolean)

    fun select(
        listCity: Array<String>,
        cityValue: String,
        state: Boolean,
        putCityValue: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener

    fun setImageUser(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView, context: Context)

}