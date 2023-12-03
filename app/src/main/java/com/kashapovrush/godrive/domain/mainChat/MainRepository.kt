package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.kashapovrush.godrive.domain.models.User

interface MainRepository {

    fun getAuth(): FirebaseAuth

    fun initUserData(
        view: ImageView,
        text: TextView,
        city: String,
        state: Boolean,
        getUser: (snapshot: DataSnapshot) -> Unit
    )

    fun setNotificationListener(
        key: String,
        context: Context,
        cityValue: String,
        textBody: String
    ): ChildEventListener


    fun removeMessageAfterTime(cityValue: String, time: Long)

    fun loggedInUserCounter(cityValue: String, randomNumber: Long)

    fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit)

    fun sendTextMessage(
        isEmptyEditText: Boolean,
        editText: EditText,
        cityValue: String,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    )

    fun getMessageKey(): String

}