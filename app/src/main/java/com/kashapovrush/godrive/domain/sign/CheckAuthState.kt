package com.kashapovrush.godrive.domain.sign

import android.content.Context
import javax.inject.Inject

class CheckAuthState @Inject constructor(private val repository: SignRepository) {

    operator fun invoke(context: Context, cls: Class<*>) {
        repository.checkAuthState(context, cls)
    }
}