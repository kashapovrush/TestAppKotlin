package com.kashapovrush.authorization_api.signin

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class PhoneNumberVerificationUseCase @Inject constructor(
    private val repository: SignInRepository
) {

    fun phoneNumberVerification(
        phoneNumber: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        repository.phoneNumberVerification(phoneNumber, activity, callback)
    }
}