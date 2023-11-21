package com.kashapovrush.godrive.domain.mainChat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import javax.inject.Inject

class InitDataUserUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(view: ImageView, text: TextView) {
       repository.initDataUser(view, text)
    }
}