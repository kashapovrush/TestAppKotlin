package com.kashapovrush.godrive.domain.userData

import android.app.Activity
import javax.inject.Inject

class ChangePhotoUserUseCase @Inject constructor(private val repository: UserDataRepository) {

    operator fun invoke(activity: Activity) {
        repository.changePhotoUser(activity)
    }
}