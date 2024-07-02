package com.kashapovrush.notification_api

import javax.inject.Inject

class ChangeNotificationUseCase @Inject constructor(private val repository: SettingsRepository) {

    fun changeStatusNotification(isSubscribedToNotification: Boolean) {
        repository.changeStatusNotification(isSubscribedToNotification)
    }
}