package com.kashapovrush.godrive.data.repository

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
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

    override fun initDataUser(view: ImageView, text: TextView) {
        AppDatabase.database.child(Constants.KEY_COLLECTION_USERS).child(AppDatabase.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java) ?: User()
                    text.text = user.city
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

    override fun setCityValue(listOfCities: Array<String>, city: String, position: Int, state: Boolean) {
        var user = User()
        if (listOfCities[position] != listOfCities[0]) {
            deletePreviousToken(city)
            AppDatabase.database.child(Constants.KEY_COLLECTION_USERS).child(AppDatabase.uid)
                .child(Constants.KEY_CITY)
                .setValue(listOfCities[position]).addOnCompleteListener {
                    if (it.isSuccessful) {
                        user.city = listOfCities[position]
                        putTokenToFirebase(listOfCities[position], state)
                    }
                }
        }
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

//    preferenceManager.getBoolean(Constants.KEY_NOTIFICATION_STATE)

}