package com.kashapovrush.settings_feature.viewmodel

import androidx.lifecycle.ViewModel
import com.kashapovrush.notification_api.ChangeNotificationUseCase
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val changeNotificationUseCase: ChangeNotificationUseCase
): ViewModel() {


    fun changeStatusNotification(isSubscribedToNotification: Boolean) {
        changeNotificationUseCase.changeStatusNotification(isSubscribedToNotification)
    }
}