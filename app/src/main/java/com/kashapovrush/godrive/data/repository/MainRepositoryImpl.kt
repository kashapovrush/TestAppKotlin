package com.kashapovrush.godrive.data.repository

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kashapovrush.godrive.data.database.AppDatabase
import com.kashapovrush.godrive.domain.mainChat.MainRepository
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

    override fun initUserData(view: ImageView, text: TextView) {
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

}