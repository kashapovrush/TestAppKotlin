package com.kashapovrush.godrive.data.database

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class AppDatabase @Inject constructor() {

    companion object {
        val auth = Firebase.auth
        val uid = auth.currentUser?.uid.toString()
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val storage: StorageReference = FirebaseStorage.getInstance().reference
    }


}