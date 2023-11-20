package com.kashapovrush.godrive.data.database

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AppDatabase @Inject constructor() {

    companion object {
        val auth = Firebase.auth
    }


}