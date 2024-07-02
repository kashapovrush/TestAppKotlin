package com.kashapovrush.notification_impl.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.kashapovrush.database.AppDatabase.auth
import com.kashapovrush.database.AppDatabase.database
import com.kashapovrush.database.AppDatabase.uid
import com.kashapovrush.notification_api.Notification
import com.kashapovrush.notification_api.SettingsRepository
import com.kashapovrush.utils.constants.Constants
import com.kashapovrush.utils.constants.Constants.Companion.KEY_NOTIFICATION_STATE
import com.kashapovrush.utils.constants.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.utils.preferences.PreferencesManager
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : SettingsRepository {
    override fun changeStatusNotification(isSubscribedToNotification: Boolean) {
        if (isSubscribedToNotification) {
            preferencesManager.putBoolean(KEY_NOTIFICATION_STATE, false)
            putTokenToFirebase(preferencesManager.getString(KEY_PREFERENCE_NAME).toString())
        } else {
            preferencesManager.putBoolean(KEY_NOTIFICATION_STATE, true)
            deletePreviousToken(preferencesManager.getString(KEY_PREFERENCE_NAME).toString())
        }
    }

    private fun putTokenToFirebase(city: String) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token = it.result
                database.child(Constants.KEY_FCM).child(city).child(uid)
                    .setValue(Notification(token))
            }
    }

    private fun deletePreviousToken(city: String) {
        database.child(Constants.KEY_FCM).child(city).child(uid).removeValue()
    }
}