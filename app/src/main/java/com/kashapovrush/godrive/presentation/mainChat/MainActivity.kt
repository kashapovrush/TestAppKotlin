package com.kashapovrush.godrive.presentation.mainChat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.R
import com.kashapovrush.godrive.presentation.sign.SignInActivity
import com.kashapovrush.godrive.presentation.adapter.ChatAdapter
import com.kashapovrush.godrive.data.database.AppDatabase
import com.kashapovrush.godrive.databinding.ActivityMainBinding
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.presentation.NotificationSettings
import com.kashapovrush.godrive.utilities.Constants.Companion.BASE_PHOTO_URL
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COUNT_APP
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_FCM
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_FILE_URL
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrive.utilities.Constants.Companion.TYPE_TEXT
import com.kashapovrush.godrive.utilities.Constants.Companion.TYPE_VOICE
import com.kashapovrush.godrive.utilities.Constants.Companion.mainActivity
import com.kashapovrush.godrive.utilities.PreferenceManager
import com.kashapovrush.godrive.utilities.SendNotification
import com.kashapovrush.godrive.utilities.ViewFactory
import com.kashapovrush.godrive.utilities.VoiceRecorder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ChatAdapter
    private lateinit var storage: StorageReference
    private lateinit var user: User
    private lateinit var notification: Notification
    private lateinit var database: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var refTextNotification: DatabaseReference
    private lateinit var refVoiceNotification: DatabaseReference
    private lateinit var voiceRecorder: VoiceRecorder
    private lateinit var messagesListener: ValueEventListener
    private lateinit var notificationTextListener: ChildEventListener
    private lateinit var notificationVoiceListener: ChildEventListener
    private lateinit var uid: String
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivity = this@MainActivity
        voiceRecorder = VoiceRecorder()
        auth = Firebase.auth
        user = User()
        notification = Notification()
        preferenceManager = PreferenceManager(applicationContext)
        storage = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        uid = auth.currentUser?.uid.toString()
        val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
        refTextNotification =
            FirebaseDatabase.getInstance().getReference(KEY_FCM).child(cityValue.toString())
        refVoiceNotification =
            FirebaseDatabase.getInstance().getReference(KEY_FCM).child(cityValue.toString())
        var keyUID = ""


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                keyUID = it.result
            }
        notificationTextListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                for (tokenSnapshot in snapshot.children) {
                    val token = tokenSnapshot.getValue(String::class.java)
                    if (keyUID != token) {
                        SendNotification.pushNotification(
                            this@MainActivity,
                            token.toString(),
                            cityValue.toString(),
                            "Новое сообщение"
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

        notificationVoiceListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                for (tokenSnapshot in snapshot.children) {
                    val token = tokenSnapshot.getValue(String::class.java)
                    if (keyUID != token) {
                        SendNotification.pushNotification(
                            this@MainActivity,
                            token.toString(),
                            cityValue.toString(),
                            "Новое голосовое сообщение"
                        )
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
    }

    override fun onResume() {
        super.onResume()
        onClickListener()
        initUser()
        initRCView()
        removeMessagesAfterTime()
        val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
        var number = (0 .. 100000000000).random()
        database.child(KEY_COUNT_APP).child(uid).child(number.toString()).setValue(cityValue.toString())
    }

    private fun removeMessagesAfterTime() {
        val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
        val time: Long = Date().time - 3 * 60 * 60 * 1000
        var del =
            database.child(KEY_PREFERENCE_NAME).child(cityValue.toString()).orderByChild("date")
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

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListener() {
        binding.inputMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.inputMessage.text.toString()
                        .isEmpty() || binding.inputMessage.text.toString() == "Запись..."
                ) {
                    binding.buttonTextSend.visibility = View.GONE
                    binding.buttonVoiceSend.visibility = View.VISIBLE
                } else {
                    binding.buttonTextSend.visibility = View.VISIBLE
                    binding.buttonVoiceSend.visibility = View.GONE
                }
            }
        })

        binding.buttonTextSend.setOnClickListener {
            if (preferenceManager.getString(KEY_PREFERENCE_NAME) == null) {
                showToast("Выберите город")
                binding.inputMessage.setText("")
            } else {
                if (binding.inputMessage.text.toString().length <= 100) {
                    if (binding.inputMessage.text.toString() != "" || binding.inputMessage.text.toString() == "Запись...") {
                        val messageKey =
                            database.child(KEY_PREFERENCE_NAME).child(user.city)
                                .push().key.toString()
                        val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
                        database.child(KEY_PREFERENCE_NAME).child(cityValue.toString())
                            .child(messageKey)
                            .setValue(
                                User(
                                    user.username,
                                    binding.inputMessage.text.toString(),
                                    TYPE_TEXT,
                                    user.photoUrl,
                                    "",
                                    messageKey,
                                    user.city,
                                    System.currentTimeMillis()
                                )
                            )
                        refTextNotification.addChildEventListener(notificationTextListener)
                        binding.inputMessage.setText("")
                    }
                } else {
                    showToast("Сообщение не отправлено! Вы пытаетесь отправить слишком длинное сообщение!")
                    binding.inputMessage.setText("")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            binding.buttonVoiceSend.setOnTouchListener { v, event ->
                if (checkPermission(android.Manifest.permission.RECORD_AUDIO)) {
                    val messageKey = database.child(KEY_PREFERENCE_NAME).push().key.toString()
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        val beginTime = System.currentTimeMillis()
                        preferenceManager.putLong("keyTime", beginTime)
                        binding.inputMessage.setText("Запись...")
                        voiceRecorder.startRecorder(messageKey)

                    } else if (event.action == MotionEvent.ACTION_UP) {

                        binding.inputMessage.setText("")
                        voiceRecorder.stopRecorder { file, messageKey ->
                            val endTime = System.currentTimeMillis()
                            if (preferenceManager.getString(KEY_PREFERENCE_NAME) != null) {
                                if (endTime - preferenceManager.getLong("keyTime") <= 10000) {
                                    uploadVoiceToStorage(
                                        Uri.fromFile(file),
                                        messageKey,
                                        TYPE_VOICE
                                    )
                                } else {
                                    showToast("Голосовое сообщение не отправлено! Вы пытаетесь отправить слишком длинное сообщение!")
                                }
                            } else {
                                showToast("Выберите город")
                            }
                        }
                    }
                }
                true
            }

        }

        binding.imageInfo.setOnClickListener {
            val menu = PopupMenu(this, binding.imageInfo)
            val inflater = menu.menuInflater
            inflater.inflate(R.menu.main_menu, menu.menu)

            menu.show()

            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.data_user -> {
                        val intent = Intent(this, UserDataActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.notification_settings -> {
                        val intent = Intent(this, NotificationSettings::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.sign_out -> {
                        auth.signOut()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                true
            }

        }

        binding.textCity.setOnClickListener {
            val intent = Intent(this@MainActivity, UserDataActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageProfileChatMessage.setOnClickListener {
            val intent = Intent(this@MainActivity, UserDataActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun uploadVoiceToStorage(uri: Uri, messageKey: String, type: String) {
        val path = storage.child(KEY_FILE_URL).child(messageKey)
        path.putFile(uri)
            .addOnSuccessListener {
                path.downloadUrl
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val fileUrl = it.result.toString()
                            user.fileUrl = fileUrl
                            database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_FILE_URL)
                                .setValue(fileUrl)
                            val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
                            database.child(KEY_PREFERENCE_NAME).child(cityValue.toString())
                                .child(messageKey).setValue(
                                    User(
                                        user.username,
                                        "",
                                        type,
                                        user.photoUrl,
                                        fileUrl,
                                        messageKey,
                                        user.city,
                                        System.currentTimeMillis()
                                    )
                                )
                            refVoiceNotification.addChildEventListener(notificationVoiceListener)
                        }
                    }
                    .addOnFailureListener {
                    }
            }
            .addOnFailureListener {
            }
    }

    private fun initUser() {
        database.child(KEY_COLLECTION_USERS).child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: User()
                    if (preferenceManager.getString(KEY_PREFERENCE_NAME) == null) {
                        binding.textCity.text = "Выберите город"
                    } else {
                        binding.textCity.text = preferenceManager.getString(KEY_PREFERENCE_NAME)
                    }
                    if (user.photoUrl.isEmpty()) {
                        Picasso.get()
                            .load(BASE_PHOTO_URL)
                            .into(binding.imageProfileChatMessage)
                    } else {
                        Picasso.get()
                            .load(user.photoUrl)
                            .into(binding.imageProfileChatMessage)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })


    }

    private fun initRCView() {
        adapter = ChatAdapter()
        binding.chatRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        binding.chatRecyclerView.layoutManager = linearLayoutManager
        val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
        refMessages =
            database.child(KEY_PREFERENCE_NAME).child(cityValue.toString())
        messagesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot: DataSnapshot in snapshot.children) {
                    val message = userSnapshot.getValue(User::class.java) ?: User()
                    adapter.setList(ViewFactory.getType(message)) {
                        binding.chatRecyclerView.smoothScrollToPosition(adapter.itemCount)

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        refMessages.addValueEventListener(messagesListener)

    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                this,
                permission
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 200)
            false
        } else true
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceRecorder.releaseRecorder()
    }

    override fun onPause() {
        super.onPause()
        refMessages.removeEventListener(messagesListener)
        refTextNotification.removeEventListener(notificationTextListener)
        refVoiceNotification.removeEventListener(notificationVoiceListener)
    }

    override fun onBackPressed() {

    }

    fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}