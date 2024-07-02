package com.kashapovrush.authorization_impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.authorization_api.entercode.EnterCodeRepository
import javax.inject.Inject

class EnterCodeRepositoryImpl @Inject constructor(): EnterCodeRepository {

    private val auth: FirebaseAuth = Firebase.auth
    override fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(credential)
    }
}