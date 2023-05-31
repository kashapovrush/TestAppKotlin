package com.kashapovrush.godrivekotlin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrivekotlin.databinding.ActivityUserDataBinding
import com.kashapovrush.godrivekotlin.models.Notification
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.BASE_PHOTO_URL
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_CHILD_USERNAME
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_CITY
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERNAMES
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_FCM
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_FILE_URL
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PHOTO_URL
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PROFILE_IMAGE
import com.kashapovrush.godrivekotlin.utilities.PreferenceManager
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User
    private lateinit var newUsername: String
    private lateinit var selectCity: Spinner
    private lateinit var preferenceManager: PreferenceManager
    val listCity = arrayOf("Выберите город", "Туймазы", "Октябрьский", "Шаран", "Кандры")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = User()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()
        preferenceManager = PreferenceManager(applicationContext)
        initDataUser()
        selectCity = binding.selectCity
        var arrayAdapter = ArrayAdapter<String>(
            this@UserDataActivity,
            android.R.layout.simple_spinner_dropdown_item,
            listCity
        )
        selectCity.adapter = arrayAdapter
        selectCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val uid = auth.currentUser?.uid.toString()
                if (listCity[position] != listCity[0]) {
                    val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
                    deletePreviousToken(cityValue.toString())
                    database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_CITY)
                        .setValue(listCity[position]).addOnCompleteListener {
                            if (it.isSuccessful) {
                                preferenceManager.putString(KEY_PREFERENCE_NAME, listCity[position])
                                user.city = listCity[position]
                                putTokenToFirebase(listCity[position])
                            }
                        }

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.saveData.setOnClickListener {
            change()
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.layoutImage.setOnClickListener {
            changePhotoUser()
        }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(200, 200)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uid = auth.currentUser?.uid.toString()
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = storage.child(KEY_PROFILE_IMAGE).child(uid)
            path.putFile(uri).addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val photoUrl = task2.result.toString()
                            database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_PHOTO_URL)
                                .setValue(photoUrl)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        user.photoUrl = photoUrl
                                        Picasso.get()
                                            .load(user.photoUrl)
                                            .into(binding.imageProfile)
                                        toastShow("Данные изменены")

                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun initDataUser() {
        val uid = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERS).child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: User()
                    binding.inputName.setText(user.username)
                    binding.choiseCity.text = user.city
                    if (user.photoUrl.isEmpty()) {
                        Picasso.get()
                            .load(BASE_PHOTO_URL)
                            .into(binding.imageProfile)
//                        binding.imageProfile.setImageResource(com.kashapovrush.godrivekotlin.R.drawable.ic_base_photo_camera)
                    }
                    else {
                        Picasso.get()
                            .load(user.photoUrl)
                            .into(binding.imageProfile)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun change() {
        val uid = auth.currentUser?.uid.toString()
        newUsername = binding.inputName.text.toString()
        if (newUsername.isEmpty()) {
            toastShow("Введите username")
        } else {
            database.child(KEY_COLLECTION_USERNAMES)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                            changeUsername()
                        }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }

    private fun changeUsername() {
        val uid: String = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERNAMES).child(newUsername).setValue(uid)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        val uid = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_CHILD_USERNAME)
            .setValue(newUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toastShow("Данные обновлены")
                    deletedOldUsername()
                } else {
                    toastShow("Error")
                }
            }
    }

    private fun deletedOldUsername() {
        database.child(KEY_COLLECTION_USERNAMES).child(user.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toastShow("Успешно")
                    user.username = newUsername
                }
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
                database.child(KEY_FCM).child(city).child(uid).setValue(Notification(token))
            }
    }

    private fun deletePreviousToken(city: String) {
        val uid = auth.currentUser?.uid.toString()
        database.child(KEY_FCM).child(city).child(uid).removeValue()
    }

    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}