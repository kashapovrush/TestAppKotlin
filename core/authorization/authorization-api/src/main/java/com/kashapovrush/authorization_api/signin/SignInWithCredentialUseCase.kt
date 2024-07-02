package com.kashapovrush.authorization_api.signin

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(
    private val repository: SignInRepository
) {

    fun signInWithCredential(code: String) {
        repository.signInWithCredential(code)
    }

    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult> {
        return repository.signInWithCredential(credential)
    }
}