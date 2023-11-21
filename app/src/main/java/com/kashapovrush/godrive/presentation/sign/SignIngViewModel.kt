package com.kashapovrush.godrive.presentation.sign

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kashapovrush.godrive.domain.sign.CheckAuthState
import com.kashapovrush.godrive.domain.sign.PhoneNumberVerificationUseCase
import com.kashapovrush.godrive.domain.sign.SignInWithCredentialUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignIngViewModel @Inject constructor(
    private val phoneNumberVerificationUseCase: PhoneNumberVerificationUseCase,
    private val checkAuthState: CheckAuthState,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase
) : ViewModel() {

    fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult> {
        return signInWithCredentialUseCase.signInWithCredentialUC(credential)
    }

    fun phoneNumberVerification(
        number: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        viewModelScope.launch {
            phoneNumberVerificationUseCase(number, activity, callbacks)
        }

    }

    fun checkAutoState(context: Context, cls: Class<*>) {
        checkAuthState(context, cls)
    }
}