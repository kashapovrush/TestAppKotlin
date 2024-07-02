package com.kashapovrush.authorization_api.entercode

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential

interface EnterCodeRepository {

    fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult>
}