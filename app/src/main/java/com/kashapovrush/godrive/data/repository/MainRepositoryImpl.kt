package com.kashapovrush.godrive.data.repository

//import android.content.Context
//import android.net.Uri
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.MotionEvent
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.ChildEventListener
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.FirebaseStorage
//import com.kashapovrush.godrive.domain.mainChat.MainRepository
//import com.kashapovrush.godrive.domain.models.Notification
//import com.kashapovrush.godrive.domain.models.User
//import com.kashapovrush.utils.constants.Constants
//import com.kashapovrush.godrive.utilities.SendNotification
//import com.kashapovrush.godrive.utilities.ViewFactory
//import com.kashapovrush.godrive.utilities.VoiceRecorder
//import com.squareup.picasso.Picasso
//import javax.inject.Inject

//class MainRepositoryImpl @Inject constructor(contextApp: Context) :
//    MainRepository {
//
//    private var auth = Firebase.auth
//    private val voiceRecorder = VoiceRecorder()
////    val preferenceManager =
////        com.kashapovrush.utils.preferencesManager.PreferenceManagerImpl(contextApp)
//
//    override fun getAuth(): FirebaseAuth {
//        return Firebase.auth
//    }
//
//    override fun initUserData(
//        view: ImageView,
//        text: TextView,
//        getUser: (snapshot: DataSnapshot) -> Unit
//    ) {
//        val uid = auth.currentUser?.uid.toString()
//        val database = FirebaseDatabase.getInstance().reference
//        database.child(Constants.KEY_COLLECTION_USERS).child(uid)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    getUser(snapshot)
//                    var user = snapshot.getValue(User::class.java) ?: User()
//                    if (preferenceManager.getString(Constants.KEY_PREFERENCE_NAME) == null) {
//                        text.text = "Выберите город"
//                    } else {
//                        text.text =
//                            preferenceManager.getString(Constants.KEY_PREFERENCE_NAME).toString()
//                    }
//                    if (user.photoUrl.isEmpty()) {
//                        Picasso.get()
//                            .load(Constants.BASE_PHOTO_URL)
//                            .into(view)
//                    } else {
//                        Picasso.get()
//                            .load(user.photoUrl)
//                            .into(view)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//    }
//
//    override fun setNotificationListener(
//        key: String,
//        context: Context,
//        textBody: String
//    ): ChildEventListener {
//        return object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                for (tokenSnapshot in snapshot.children) {
//                    val token = tokenSnapshot.getValue(String::class.java)
//                    if (key != token) {
//                        SendNotification.pushNotification(
//                            context,
//                            token.toString(),
//                            preferenceManager.getString(Constants.KEY_PREFERENCE_NAME).toString(),
//                            textBody
//                        )
//                    }
//                }
//            }
//
//            override fun onChildChanged(
//                snapshot: DataSnapshot,
//                previousChildName: String?
//            ) {
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        }
//    }
//
//    override fun removeMessageAfterTime(time: Long) {
//        val database = FirebaseDatabase.getInstance().reference
//        val cityValue = preferenceManager.getString(Constants.KEY_PREFERENCE_NAME).toString()
//        var del =
//            database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue).orderByChild("date")
//                .endAt(time.toDouble())
//
//        del.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (itemSnapshot: DataSnapshot in snapshot.children) {
//                    itemSnapshot.ref.removeValue()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }
//
//    override fun loggedInUserCounter(randomNumber: Long) {
//        val uid = auth.currentUser?.uid.toString()
//        val database = FirebaseDatabase.getInstance().reference
//        database.child(Constants.KEY_COUNT_APP).child(uid).child(randomNumber.toString())
//            .setValue(preferenceManager.getString(Constants.KEY_PREFERENCE_NAME))
//    }
//
//    override fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit) {
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                afterTextChanged()
//            }
//        })
//    }
//
//    override fun sendTextMessage(
//        editText: EditText,
//        addChildEventListener: () -> Unit,
//        context: Context,
//        messageKey: String,
//        user: User,
//        time: Long
//    ) {
//        val database = FirebaseDatabase.getInstance().reference
//        if (preferenceManager.getString(Constants.KEY_PREFERENCE_NAME) == null) {
//            showToast(context, "Выберите город")
//            editText.setText("")
//        } else {
//            if (editText.text.toString().length <= 100) {
//                if (editText.text.toString() != "" || editText.text.toString() == "Запись...") {
//                    val cityValue =
//                        preferenceManager.getString(Constants.KEY_PREFERENCE_NAME).toString()
//                    database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue)
//                        .child(messageKey)
//                        .setValue(
//                            User(
//                                user.username,
//                                editText.text.toString(),
//                                Constants.TYPE_TEXT,
//                                user.photoUrl,
//                                "",
//                                messageKey,
//                                user.city,
//                                time
//                            )
//                        )
//                    addChildEventListener()
//                    editText.setText("")
//                }
//            } else {
//                showToast(
//                    context,
//                    "Сообщение не отправлено! Вы пытаетесь отправить слишком длинное сообщение!"
//                )
//                editText.setText("")
//            }
//        }
//    }
//
//    override fun getMessageKey(): String {
//        val database = FirebaseDatabase.getInstance().reference
//        val user = User()
//        return database.child(Constants.KEY_PREFERENCE_NAME).child(user.city)
//            .push().key.toString()
//    }
//
//    override fun getUser(): User {
//        return User()
//    }
//
//    override fun getNotification(): Notification {
//        return Notification()
//    }
//
//    override fun getReferenceNotification(): DatabaseReference {
//        val cityValue = preferenceManager.getString(Constants.KEY_PREFERENCE_NAME)
//        return FirebaseDatabase.getInstance().getReference(Constants.KEY_FCM)
//            .child(cityValue.toString())
//    }
//
//    override fun initRCView(
//        rv: RecyclerView,
//        llm: LinearLayoutManager
//    ) {
//        val database = FirebaseDatabase.getInstance().reference
//        val adapter = ChatAdapter()
//        rv.adapter = adapter
//        rv.layoutManager = llm
//        val cityValue = preferenceManager.getString(Constants.KEY_PREFERENCE_NAME)
//        val refMessages =
//            database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue.toString())
//         refMessages.addValueEventListener( object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (userSnapshot: DataSnapshot in snapshot.children) {
//                    val message = userSnapshot.getValue(User::class.java) ?: User()
//                    adapter.setList(ViewFactory.getType(message)) {
//                        rv.smoothScrollToPosition(adapter.itemCount)
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//    }
//
//    override fun sendVoiceMessage(
//        isCheckPermission: Boolean,
//        messageKey: String,
//        event: MotionEvent,
//        editText: EditText,
//        user: User,
//        context: Context,
//        addChildEventListener: () -> Unit,
//    ) {
//        if (isCheckPermission) {
//            var begin: Long
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                begin = System.currentTimeMillis()
//                preferenceManager.putLong("keyTime", begin)
//                editText.setText("Запись...")
//                voiceRecorder.startRecorder(messageKey)
//
//            } else if (event.action == MotionEvent.ACTION_UP) {
//
//                editText.setText("")
//                voiceRecorder.stopRecorder { file, messageKey ->
//                    var end = System.currentTimeMillis()
//                    if (preferenceManager.getString(Constants.KEY_PREFERENCE_NAME) != null) {
//                        if (end - preferenceManager.getLong("keyTime") <= 10000) {
//
//                            uploadVoiceToStorage(
//                                Uri.fromFile(file),
//                                user,
//                                messageKey,
//                                Constants.TYPE_VOICE,
//                                addChildEventListener
//                            )
//                        } else {
//                            showToast(
//                                context,
//                                "Голосовое сообщение не отправлено! Вы пытаетесь отправить слишком длинное сообщение!"
//                            )
//                        }
//                    } else {
//                        showToast(context, "Выберите город")
//                    }
//                }
//            }
//        }
//
//    }
//
//    private fun uploadVoiceToStorage(
//        uri: Uri,
//        user: User,
//        messageKey: String,
//        type: String,
//        addChildEventListener: () -> Unit
//    ) {
//        val storage = FirebaseStorage.getInstance().reference
//        val database = FirebaseDatabase.getInstance().reference
//        val uid = auth.currentUser?.uid.toString()
//        val path = storage.child(Constants.KEY_FILE_URL).child(messageKey)
//        path.putFile(uri)
//            .addOnSuccessListener {
//                path.downloadUrl
//                    .addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            val fileUrl = it.result.toString()
//                            user.fileUrl = fileUrl
//                            database.child(Constants.KEY_COLLECTION_USERS).child(uid).child(
//                                Constants.KEY_FILE_URL
//                            )
//                                .setValue(fileUrl)
//                            val cityValue =
//                                preferenceManager.getString(Constants.KEY_PREFERENCE_NAME)
//                            database.child(Constants.KEY_PREFERENCE_NAME)
//                                .child(cityValue.toString())
//                                .child(messageKey).setValue(
//                                    User(
//                                        user.username,
//                                        "",
//                                        type,
//                                        user.photoUrl,
//                                        fileUrl,
//                                        messageKey,
//                                        user.city,
//                                        System.currentTimeMillis()
//                                    )
//                                )
//                            addChildEventListener
//                        }
//                    }
//                    .addOnFailureListener {
//                    }
//            }
//            .addOnFailureListener {
//            }
//    }
//
//    private fun showToast(context: Context, message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//}