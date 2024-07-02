package com.kashapovrush.authorization_impl

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.authorization_api.signin.SignInRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor() : SignInRepository {

    private var auth = Firebase.auth
    override fun phoneNumberVerification(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+7$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun signInWithCredential(code: String) {
        Log.d("MainActivityTest", "code $code")
    }

    override fun checkAuthState(success: () -> Unit, failure: () -> Unit) {
        if (auth.currentUser != null) {
            success()
        } else {
            failure()
        }
    }

    override fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult> {
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

    companion object {
        const val AUTH_NONE = 0
        const val AUTH_SUCCESS = 1
        const val AUTH_ERROR = 2
    }
}