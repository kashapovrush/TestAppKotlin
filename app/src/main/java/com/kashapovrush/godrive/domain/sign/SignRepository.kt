package com.kashapovrush.godrive.domain.sign

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface SignRepository {

    fun phoneNumberVerification(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    fun checkAuthState(context: Context, cls: Class<*>)

    fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult>
}