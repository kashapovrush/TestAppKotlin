package com.kashapovrush.authorization_api.signin

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

interface SignInRepository {

    fun phoneNumberVerification(
        phoneNumber: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    fun signInWithCredential(code: String)

    fun checkAuthState(success: () -> Unit, failure: () -> Unit)

    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>

    fun getUid(): String

    fun getDatabaseReference(): DatabaseReference

    fun getStorageReference(): StorageReference
}