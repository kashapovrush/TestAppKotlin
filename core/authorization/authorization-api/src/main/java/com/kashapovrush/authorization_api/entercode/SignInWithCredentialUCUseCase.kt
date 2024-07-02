package com.kashapovrush.authorization_api.entercode

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class SignInWithCredentialUCUseCase @Inject constructor(private val repository: EnterCodeRepository) {

    fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult> {
        return repository.signInWithCredentialUC(credential)
    }
}