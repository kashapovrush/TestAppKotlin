package com.kashapovrush.godrivekotlin.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kashapovrush.godrivekotlin.R
import com.kashapovrush.godrivekotlin.activities.MainActivity
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants
import java.util.*

class ServiceMessaging: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i("Rush", "token: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message != null) {
            try {
                var title = message.notification!!.title
                var body = message.notification!!.body
                val notificationId = Random().nextInt()

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                var builder: NotificationCompat.Builder = NotificationCompat.Builder(
                    this,
                    Constants.CHANNEL_ID
                )
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_notifications)

                val notificationManager =
                    NotificationManagerCompat.from(this)
                val notificationChannel = NotificationChannel(
                    Constants.CHANNEL_ID,
                    Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(notificationChannel)

                notificationManager.notify(notificationId, builder.build())
                Log.i("Rush", "onMessageReceived")
            } catch (e: Exception) {
                Log.i("RushMessageReceived", e.message.toString())
            }
        }
        super.onMessageReceived(message)
    }
}