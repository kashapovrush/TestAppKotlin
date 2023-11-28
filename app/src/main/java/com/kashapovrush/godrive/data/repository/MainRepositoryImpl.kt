package com.kashapovrush.godrive.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kashapovrush.godrive.data.database.AppDatabase
import com.kashapovrush.godrive.domain.mainChat.MainRepository
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.utilities.Constants
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor() : MainRepository {

    override fun changePhotoUser(activity: Activity) {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(200, 200)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(activity)
    }

    override fun initUserData(view: ImageView, text: TextView, state: Boolean) {
        AppDatabase.database.child(Constants.KEY_COLLECTION_USERS).child(AppDatabase.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   var user = snapshot.getValue(User::class.java) ?: User()
                    if (state) {
                        text.text = user.city
                    } else {
                        text.text = "Выберите город"
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

    override fun select(
        listCity: Array<String>,
        cityValue: String,
        state: Boolean,
        putCityValue: (pos: Int) -> Unit
    ) =
        object : AdapterView.OnItemSelectedListener {
            var user = User()

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listCity[position] != listCity[0]) {
                    deletePreviousToken(cityValue)
                    AppDatabase.database.child(Constants.KEY_COLLECTION_USERS)
                        .child(AppDatabase.uid).child(Constants.KEY_CITY)
                        .setValue(listCity[position]).addOnCompleteListener {
                            if (it.isSuccessful) {
                                putCityValue(position)
                                user.city = listCity[position]
                                putTokenToFirebase(listCity[position], state)
                            }
                        }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    override fun setImageUser(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView, context: Context) {
        var user = User()
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = AppDatabase.storage.child(Constants.KEY_PROFILE_IMAGE).child(AppDatabase.uid)
            path.putFile(uri).addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val photoUrl = task2.result.toString()
                            AppDatabase.database.child(Constants.KEY_COLLECTION_USERS).child(AppDatabase.uid).child(
                                Constants.KEY_PHOTO_URL
                            )
                                .setValue(photoUrl)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        user.photoUrl = photoUrl
                                        Picasso.get()
                                            .load(user.photoUrl)
                                            .into(view)
                                        toastShow(context, "Данные изменены")

                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun toastShow(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun deletePreviousToken(city: String) {
        AppDatabase.database.child(Constants.KEY_FCM).child(city).child(AppDatabase.uid)
            .removeValue()
    }

    private fun putTokenToFirebase(city: String, state: Boolean) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token = it.result
                if (state) {
                    AppDatabase.database.child(Constants.KEY_FCM).child(city).child(AppDatabase.uid)
                        .setValue(Notification(token))
                }
            }
    }

}