package com.kashapovrush.godrive.domain.userData

import android.widget.ImageView
import android.widget.TextView
import javax.inject.Inject

class InitDataUserUseCase @Inject constructor(private val repository: UserDataRepository) {

    operator fun invoke(view: ImageView, text: TextView, state: Boolean) {
       repository.initUserData(view, text, state)
    }
}