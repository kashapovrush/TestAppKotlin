package com.kashapovrush.godrive.domain.mainChat

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class GetReferenceNotificationUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(): DatabaseReference {
        return repository.getReferenceNotification()
    }
}