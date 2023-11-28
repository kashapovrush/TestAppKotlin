package com.kashapovrush.godrive.presentation.mainChat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.data.database.AppDatabase
import com.kashapovrush.godrive.databinding.ActivityUserDataBinding
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.presentation.Application
import com.kashapovrush.godrive.presentation.ViewModelFactory
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.Constants.Companion.BASE_PHOTO_URL
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_CITY
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_FCM
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_NOTIFICATION_STATE
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PHOTO_URL
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PROFILE_IMAGE
import com.kashapovrush.godrive.utilities.PreferenceManager
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import javax.inject.Inject

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDataBinding
    private lateinit var storage: StorageReference
    private lateinit var user: User
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var uid: String
    private lateinit var database: DatabaseReference
    private lateinit var viewModel: UserDataViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    val listCity = arrayOf(
        "Выберите город",
        "Уфа",
        "Трасса М5",
        "Октябрьский",
        "Туймазы",
        "Чишмы",
        "Кандры",
        "Буздяк",
        "Шаран",
        "Языково"
    )

    private val component by lazy {
        (application as Application).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = User()
        viewModel = ViewModelProvider(this, viewModelFactory)[UserDataViewModel::class.java]
        uid = viewModel.getUid()
        database = viewModel.getDatabaseReference()
        storage = viewModel.getStorageReference()
        preferenceManager = PreferenceManager(applicationContext)

    }

    override fun onResume() {
        super.onResume()
        viewModel.initUserData(
                binding.choiseCity,
        binding.imageProfile,
        preferenceManager.getString(KEY_PREFERENCE_NAME) != null
        )
        var arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listCity
        )
        binding.selectCity.adapter = arrayAdapter
        binding.selectCity.onItemSelectedListener = viewModel.selectedCity(
            listCity,
            preferenceManager.getString(KEY_PREFERENCE_NAME).toString(),
            preferenceManager.getBoolean(KEY_NOTIFICATION_STATE),
            putCityValue = {
                preferenceManager.putString(KEY_PREFERENCE_NAME, listCity[it])
            }
        )

        binding.buttonBack.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        binding.layoutImage.setOnClickListener {
            viewModel.changePhotoUser(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.setImageUser(requestCode, resultCode, data, binding.imageProfile, this)

    }

    override fun onBackPressed() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }
}