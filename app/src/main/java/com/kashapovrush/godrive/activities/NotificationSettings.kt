package com.kashapovrush.godrive.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.kashapovrush.godrive.databinding.ActivityNotificationSettingsBinding
import com.kashapovrush.godrive.models.Notification
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_NOTIFICATION_STATE
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrive.utilities.PreferenceManager

class NotificationSettings : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSettingsBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        preferenceManager = PreferenceManager(applicationContext)

        binding.onNotification.isChecked = preferenceManager.getBoolean(KEY_NOTIFICATION_STATE)

        binding.onNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                preferenceManager.putBoolean(KEY_NOTIFICATION_STATE, true)
                putTokenToFirebase(preferenceManager.getString(KEY_PREFERENCE_NAME).toString())
            } else {
                preferenceManager.putBoolean(KEY_NOTIFICATION_STATE, false)
                deletePreviousToken(preferenceManager.getString(KEY_PREFERENCE_NAME).toString())
            }
        }

        binding.backToMain.setOnClickListener {
            val intent = Intent(this@NotificationSettings, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun putTokenToFirebase(city: String) {
        val uid = auth.currentUser?.uid.toString()
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token = it.result
                database.child(Constants.KEY_FCM).child(city).child(uid).setValue(Notification(token))
            }
    }

    private fun deletePreviousToken(city: String) {
        val uid = auth.currentUser?.uid.toString()
        database.child(Constants.KEY_FCM).child(city).child(uid).removeValue()
    }
}