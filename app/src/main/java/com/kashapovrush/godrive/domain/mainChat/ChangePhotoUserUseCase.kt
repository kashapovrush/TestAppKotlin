package com.kashapovrush.godrive.domain.mainChat

import android.app.Activity
import javax.inject.Inject

class ChangePhotoUserUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(activity: Activity) {
        repository.changePhotoUser(activity)
    }
}