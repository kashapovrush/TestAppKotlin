package com.kashapovrush.godrive.presentation.mainChat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.R
import com.kashapovrush.godrive.databinding.ActivityMainBinding
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.presentation.Application
import com.kashapovrush.godrive.presentation.NotificationSettings
import com.kashapovrush.godrive.presentation.ViewModelFactory
import com.kashapovrush.godrive.presentation.sign.SignInActivity
import com.kashapovrush.godrive.utilities.VoiceRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
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
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val component by lazy {
        (application as Application).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        voiceRecorder = VoiceRecorder(this@MainActivity)
        auth = viewModel.getAuth()
        uid = viewModel.getUid()
        storage = viewModel.getStorageReference()
        database = viewModel.getDatabaseReference()
        user = viewModel.getUser()
        initUser()
        notification = viewModel.getNotification()
        refTextNotification =viewModel.getReferenceNotification()
        refVoiceNotification =viewModel.getReferenceNotification()
        var keyUID = ""


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                keyUID = it.result
            }
        notificationTextListener = viewModel.setNotification(
            keyUID,
            this,
            "Новое сообщение"
        )

        notificationVoiceListener = viewModel.setNotification(
            keyUID,
            this,
            "Новое голосовое сообщение"
        )
    }

    override fun onResume() {
        super.onResume()
        onClickListener()
        initRCView()
        removeMessagesAfterTime()
        loggedInUserCounter()
    }

    private fun loggedInUserCounter() {
        var number = (0..100000000000).random()
        viewModel.loggedInUserCounter(number)
    }

    private fun removeMessagesAfterTime() {
        val time: Long = Date().time - 3 * 60 * 60 * 1000
        viewModel.removeMessageAfterTime(time)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListener() {
        viewModel.showButtonVoice(binding.inputMessage) {
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

        binding.buttonTextSend.setOnClickListener {
            Log.d("TestUserData", "click")
            viewModel.sendTextMessage(
                binding.inputMessage,
                {
                    refTextNotification.addChildEventListener(notificationTextListener)
                },
                this@MainActivity,
                viewModel.getMessageKey(),
                user,
                System.currentTimeMillis()
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            binding.buttonVoiceSend.setOnTouchListener { _, event ->
                viewModel.sendVoiceMessage(
                    checkPermission(android.Manifest.permission.RECORD_AUDIO),
                    viewModel.getMessageKey(),
                    event,
                    binding.inputMessage,
                    user,
                    this@MainActivity)
                 {
                    refVoiceNotification.addChildEventListener(notificationVoiceListener)
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
                        startActivity(UserDataActivity.newIntent(this))
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
            startActivity(UserDataActivity.newIntent(this))
            finish()
        }

        binding.imageProfileChatMessage.setOnClickListener {
            startActivity(UserDataActivity.newIntent(this))
            finish()
        }

    }

    private fun initUser() {
        viewModel.initUserData(
            binding.imageProfileChatMessage,
            binding.textCity
        ) {
            user = it.getValue(User::class.java) ?: User()
        }
    }

    private fun initRCView() {
        viewModel.initRCView(
            binding.chatRecyclerView,
            LinearLayoutManager(this@MainActivity),
            this@MainActivity
        )
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

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}