package com.kashapovrush.godrivekotlin.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrivekotlin.R
import com.kashapovrush.godrivekotlin.activities.sign.SignInActivity
import com.kashapovrush.godrivekotlin.adapter.ChatAdapter
import com.kashapovrush.godrivekotlin.databinding.ActivityMainBinding
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_FILE_URL
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_MESSAGE
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PHOTO_URL
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_TYPE_MESSAGE
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.TYPE_TEXT
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.TYPE_VOICE
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.mainActivity
import com.kashapovrush.godrivekotlin.utilities.ViewFactory
import com.kashapovrush.godrivekotlin.utilities.VoiceRecorder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ChatAdapter
    private lateinit var storage: StorageReference
    private lateinit var user: User
    private lateinit var database: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var voiceRecorder: VoiceRecorder
    private lateinit var messagesListener: ValueEventListener
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivity = this@MainActivity
        voiceRecorder = VoiceRecorder()
        auth = Firebase.auth
        user = User()
        storage = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        uid = auth.currentUser?.uid.toString()
        setUpActionBar()
        initUser()
        initRCView()
        mainActivity.title = "Title"
    }

    override fun onStart() {
        super.onStart()
        onClickListener()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListener() {
        val myRef = Firebase.database.getReference(KEY_PREFERENCE_NAME)


        binding.layoutSend.setOnClickListener {
            if (binding.inputMessage.text.toString() != "" || binding.inputMessage.text.toString() == "Record...") {
                val messageKey = myRef.push().key.toString()
                myRef.child(messageKey).setValue(
                    User(
                        user.username,
                        binding.inputMessage.text.toString(),
                        TYPE_TEXT,
                        "",
                        "",
                        messageKey
                    )
                )
                binding.inputMessage.setText("")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            binding.buttonVoiceSend.setOnTouchListener { v, event ->
                if (checkPermission(android.Manifest.permission.RECORD_AUDIO)) {
                    val messageKey = myRef.push().key.toString()
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        binding.inputMessage.setText("Record...")
                        voiceRecorder.startRecorder(messageKey)

                    } else if (event.action == MotionEvent.ACTION_UP) {
                        binding.inputMessage.setText("")
                        voiceRecorder.stopRecorder { file, messageKey ->
                            uploadVoiceToStorage(Uri.fromFile(file), messageKey, TYPE_VOICE)
                        }
                    }
                }
                true
            }
        }

    }

    private fun uploadVoiceToStorage(uri: Uri, messageKey: String, type: String) {
        val myRef = Firebase.database.getReference(KEY_PREFERENCE_NAME)
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
                            myRef.child(messageKey).setValue(
                                User(user.username, "", type, "", fileUrl, messageKey)
                            )

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
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: User()
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
        refMessages = database.child(KEY_PREFERENCE_NAME)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out) {
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (item.itemId == R.id.data_user) {
            val intent = Intent(this, UserDataActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpActionBar() {
        val actionBar = supportActionBar
        Thread {
            val bitmap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val drawableIcon = BitmapDrawable(resources, bitmap)
            runOnUiThread {
                actionBar?.setDisplayHomeAsUpEnabled(true)
                actionBar?.setHomeAsUpIndicator(drawableIcon)
                val titleText = database.child(KEY_PREFERENCE_NAME).key.toString()
                actionBar?.title = titleText
            }

        }.start()
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
    }

    override fun onBackPressed() {

    }
}