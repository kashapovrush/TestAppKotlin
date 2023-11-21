package com.kashapovrush.godrive.domain.sign

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(private val repository: SignRepository) {

    fun signInWithCredentialUC (credential: PhoneAuthCredential): Task<AuthResult> {
        return repository.signInWithCredentialUC(credential)
    }
}