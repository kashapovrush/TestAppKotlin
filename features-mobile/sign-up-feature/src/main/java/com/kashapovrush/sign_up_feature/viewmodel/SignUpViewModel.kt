package com.kashapovrush.sign_up_feature.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kashapovrush.authorization_api.signin.PhoneNumberVerificationUseCase
import com.kashapovrush.authorization_api.signin.SignInWithCredentialUseCase
import com.kashapovrush.sign_up_feature.ui.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val phoneNumberAuthorizationUseCase: PhoneNumberVerificationUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<State>(State.Initial)
    val authState: StateFlow<State> = _authState.asStateFlow()

    fun phoneNumberVerification(
        phoneNumber: String,
        activity: Activity
    ) {
        viewModelScope.launch {
            _authState.emit(State.Loading)
        }

        phoneNumberAuthorizationUseCase.phoneNumberVerification(phoneNumber, activity, callback)
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithCredentialUseCase.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
//                    startActivity(MainActivity.newIntent(this@SignInActivity))
//                    finish()
                    viewModelScope.launch {
                        _authState.emit(State.Success)
                    }

                } else {
//                    toastShow("Ошибка, попробуйте позже :(")
//                    stopProgressBar()
                   viewModelScope.launch {
                       _authState.emit(State.Error("Ошибка, попробуйте позже :("))
                   }
                }
            }
        }

        override fun onVerificationFailed(error: FirebaseException) {
//            toastShow("Ошибка, попробуйте позже :(")
//            stopProgressBar()
            viewModelScope.launch {
                _authState.emit(State.Error("Ошибка, попробуйте позже :("))
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
//            id = verificationId
//            preferenceManager.putString(EXTRA_SIGN_IN_ID, id)
//            startActivity(SignInEnterCodeActivity.newIntent(this@SignInActivity))
//            finish()
            viewModelScope.launch {
                _authState.emit(State.SaveId(verificationId))
            }

        }
    }
}