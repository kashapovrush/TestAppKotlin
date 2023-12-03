package com.kashapovrush.godrive.domain.mainChat

import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.kashapovrush.godrive.domain.models.User
import javax.inject.Inject

class InitUserDataUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(
        view: ImageView,
        text: TextView,
        getUser: (snapshot: DataSnapshot) -> Unit
    ) {
        repository.initUserData(view, text, getUser)
    }
}