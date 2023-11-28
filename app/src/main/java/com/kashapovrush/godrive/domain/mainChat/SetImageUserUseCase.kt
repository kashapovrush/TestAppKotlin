package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import javax.inject.Inject

class SetImageUserUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView, context: Context) {
        repository.setImageUser(requestCode, resultCode, data, view, context)
    }
}