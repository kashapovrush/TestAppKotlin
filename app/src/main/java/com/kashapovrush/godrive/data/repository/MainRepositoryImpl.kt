package com.kashapovrush.godrive.data.repository

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.domain.mainChat.MainRepository
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.SendNotification
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor() : MainRepository {

    private var auth = Firebase.auth

    override fun getAuth(): FirebaseAuth {
        return Firebase.auth
    }

    override fun initUserData(
        view: ImageView,
        text: TextView,
        city: String,
        state: Boolean,
        getUser: (snapshot: DataSnapshot) -> Unit
    ) {
        val uid = auth.currentUser?.uid.toString()
        val database = FirebaseDatabase.getInstance().reference
        database.child(Constants.KEY_COLLECTION_USERS).child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    getUser(snapshot)
                    var user = snapshot.getValue(User::class.java) ?: User()
                    if (state) {
                        text.text = "Выберите город"
                    } else {
                        text.text = city
                    }
                    if (user.photoUrl.isEmpty()) {
                        Picasso.get()
                            .load(Constants.BASE_PHOTO_URL)
                            .into(view)
                    } else {
                        Picasso.get()
                            .load(user.photoUrl)
                            .into(view)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    override fun setNotificationListener(
        key: String,
        context: Context,
        cityValue: String,
        textBody: String
    ): ChildEventListener {
        return object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                for (tokenSnapshot in snapshot.children) {
                    val token = tokenSnapshot.getValue(String::class.java)
                    if (key != token) {
                        SendNotification.pushNotification(
                            context,
                            token.toString(),
                            cityValue,
                            textBody
                        )
                    }
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
    }

    override fun removeMessageAfterTime(cityValue: String, time: Long) {
        val database = FirebaseDatabase.getInstance().reference
        var del =
            database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue).orderByChild("date")
                .endAt(time.toDouble())

        del.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot: DataSnapshot in snapshot.children) {
                    itemSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun loggedInUserCounter(cityValue: String, randomNumber: Long) {
        val uid = auth.currentUser?.uid.toString()
        val database = FirebaseDatabase.getInstance().reference
        database.child(Constants.KEY_COUNT_APP).child(uid).child(randomNumber.toString())
            .setValue(cityValue)
    }

    override fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                afterTextChanged()
            }
        })
    }

    override fun sendTextMessage(
        isEmptyEditText: Boolean,
        editText: EditText,
        cityValue: String,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    ) {
        val database = FirebaseDatabase.getInstance().reference
        if (isEmptyEditText) {
            showToast(context, "Выберите город")
            editText.setText("")
        } else {
            if (editText.text.toString().length <= 100) {
                if (editText.text.toString() != "" || editText.text.toString() == "Запись...") {
                    database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue)
                        .child(messageKey)
                        .setValue(
                            User(
                                user.username,
                                editText.text.toString(),
                                Constants.TYPE_TEXT,
                                user.photoUrl,
                                "",
                                messageKey,
                                user.city,
                                time
                            )
                        )
                    addChildEventListener()
                    editText.setText("")
                }
            } else {
                showToast(
                    context,
                    "Сообщение не отправлено! Вы пытаетесь отправить слишком длинное сообщение!"
                )
                editText.setText("")
            }
        }
    }

    override fun getMessageKey(): String {
        val database = FirebaseDatabase.getInstance().reference
        val user = User()
        return database.child(Constants.KEY_PREFERENCE_NAME).child(user.city)
            .push().key.toString()
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}