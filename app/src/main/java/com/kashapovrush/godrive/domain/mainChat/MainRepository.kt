package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User

interface MainRepository {

    fun getAuth(): FirebaseAuth

    fun initUserData(
        view: ImageView,
        text: TextView,
        getUser: (snapshot: DataSnapshot) -> Unit
    )

    fun setNotificationListener(
        key: String,
        context: Context,
        textBody: String
    ): ChildEventListener


    fun removeMessageAfterTime(time: Long)

    fun loggedInUserCounter(randomNumber: Long)

    fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit)

    fun sendTextMessage(
        editText: EditText,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    )

    fun getMessageKey(): String

    fun sendVoiceMessage(
        isCheckPermission: Boolean,
        messageKey: String,
        event: MotionEvent,
        editText: EditText,
        user: User,
        context: Context,
        addChildEventListener: () -> Unit,
    )

    fun getUser(): User

    fun getNotification(): Notification

    fun getReferenceNotification(): DatabaseReference

    fun initRCView(rv: RecyclerView, llm: LinearLayoutManager)

}