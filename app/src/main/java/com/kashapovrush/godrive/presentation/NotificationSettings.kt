package com.kashapovrush.godrive.presentation

import androidx.appcompat.app.AppCompatActivity

class NotificationSettings : AppCompatActivity() {
//
//    private lateinit var binding: ActivityNotificationSettingsBinding
//    private lateinit var preferenceManager: com.kashapovrush.utils.preferencesManager.PreferenceManagerImpl
//    private lateinit var database: DatabaseReference
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().reference
//        preferenceManager =
//            com.kashapovrush.utils.preferencesManager.PreferenceManagerImpl(applicationContext)
//
//        binding.onNotification.isChecked = preferenceManager.getBoolean(KEY_NOTIFICATION_STATE)
//
//        binding.onNotification.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                preferenceManager.putBoolean(KEY_NOTIFICATION_STATE, true)
//                putTokenToFirebase(preferenceManager.getString(KEY_PREFERENCE_NAME).toString())
//            } else {
//                preferenceManager.putBoolean(KEY_NOTIFICATION_STATE, false)
//                deletePreviousToken(preferenceManager.getString(KEY_PREFERENCE_NAME).toString())
//            }
//        }
//
//        binding.backToMain.setOnClickListener {
//            val intent = Intent(this@NotificationSettings, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (Build.VERSION.SDK_INT >= 33) {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_DENIED
//            ) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                    1
//                )
//            }
//        }
//    }
//
//    private fun putTokenToFirebase(city: String) {
//        val uid = auth.currentUser?.uid.toString()
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener {
//                if (!it.isSuccessful) {
//                    return@addOnCompleteListener
//                }
//                val token = it.result
//                database.child(Constants.KEY_FCM).child(city).child(uid)
//                    .setValue(Notification(token))
//            }
//    }
//
//    private fun deletePreviousToken(city: String) {
//        val uid = auth.currentUser?.uid.toString()
//        database.child(Constants.KEY_FCM).child(city).child(uid).removeValue()
//    }
//
//    @SuppressLint("MissingSuperCall")
//    override fun onBackPressed() {
//        startActivity(MainActivity.newIntent(this))
//        finish()
//    }
}