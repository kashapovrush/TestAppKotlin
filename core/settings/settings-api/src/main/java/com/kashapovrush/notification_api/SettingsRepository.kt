package com.kashapovrush.notification_api

interface SettingsRepository {

    fun changeStatusNotification(isSubscribedToNotification: Boolean)
}