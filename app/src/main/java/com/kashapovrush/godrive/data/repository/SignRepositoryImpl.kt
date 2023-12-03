package com.kashapovrush.godrive.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.data.database.AppDatabase
import com.kashapovrush.godrive.domain.sign.SignRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor() : SignRepository {

    private var auth: FirebaseAuth = Firebase.auth


    override fun phoneNumberVerification(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun checkAuthState(context: Context, cls: Class<*>) {
        if (auth.currentUser != null) {
            context.startActivity(Intent(context, cls))
        }
    }

    override fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(credential)
    }

    override fun getUid(): String {
        return auth.currentUser?.uid.toString()
    }

    override fun getDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    override fun getStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

}