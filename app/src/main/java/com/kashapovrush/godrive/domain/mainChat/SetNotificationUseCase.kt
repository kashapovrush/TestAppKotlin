package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import com.google.firebase.database.ChildEventListener
import javax.inject.Inject

class SetNotificationUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(key: String, context: Context, cityValue: String, textBody: String): ChildEventListener {
        return repository.setNotificationListener(key, context, cityValue, textBody)
    }
}