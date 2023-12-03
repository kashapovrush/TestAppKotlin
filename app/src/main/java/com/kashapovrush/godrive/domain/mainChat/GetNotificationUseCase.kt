package com.kashapovrush.godrive.domain.mainChat

import com.kashapovrush.godrive.domain.models.Notification
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(): Notification {
        return repository.getNotification()
    }
}