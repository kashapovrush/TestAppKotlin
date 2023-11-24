package com.kashapovrush.godrive.presentation.mainChat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.databinding.ActivityUserDataBinding
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.presentation.Application
import com.kashapovrush.godrive.presentation.ViewModelFactory
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COLLECTION_USERS
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
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var uid: String
    private lateinit var database: DatabaseReference
    private lateinit var viewModel: UserDataViewModel
    private lateinit var user: User

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

        viewModel = ViewModelProvider(this, viewModelFactory)[UserDataViewModel::class.java]
        uid = viewModel.getUid()
        database = viewModel.getDatabaseReference()
        storage = viewModel.getStorageReference()
        preferenceManager = PreferenceManager(applicationContext)

        viewModel.initUserData(binding.imageProfile, binding.choiseCity)
        var arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listCity
        )
        binding.selectCity.adapter = arrayAdapter
        binding.selectCity.onItemSelectedListener = onItemSelectedListener()

        binding.buttonBack.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        binding.layoutImage.setOnClickListener {
            viewModel.changePhotoUser(this)
        }
    }

    private fun onItemSelectedListener() = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val cityValue = preferenceManager.getString(KEY_PREFERENCE_NAME)
            val state = preferenceManager.getBoolean(KEY_NOTIFICATION_STATE)
            viewModel.setCityValue(listCity, cityValue.toString(), position, state)
            preferenceManager.putString(KEY_PREFERENCE_NAME, listCity[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
            && data != null
        ) {

            var user = User()
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




    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onBackPressed() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }
}