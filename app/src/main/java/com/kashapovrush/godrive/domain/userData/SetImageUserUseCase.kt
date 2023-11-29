package com.kashapovrush.godrive.domain.userData

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import javax.inject.Inject

class SetImageUserUseCase @Inject constructor(private val repository: UserDataRepository) {

    operator fun invoke(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView, context: Context) {
        repository.setImageUser(requestCode, resultCode, data, view, context)
    }
}