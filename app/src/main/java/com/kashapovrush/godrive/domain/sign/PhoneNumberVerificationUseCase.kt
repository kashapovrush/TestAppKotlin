package com.kashapovrush.godrive.domain.sign

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class PhoneNumberVerificationUseCase @Inject constructor(private val repository: SignRepository) {

    operator fun invoke(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        repository.phoneNumberVerification(number, activity, callbacks)
    }
}