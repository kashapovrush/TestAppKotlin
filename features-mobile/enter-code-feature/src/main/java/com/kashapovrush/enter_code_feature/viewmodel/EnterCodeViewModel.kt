package com.kashapovrush.enter_code_feature.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import com.kashapovrush.authorization_api.entercode.SignInWithCredentialUCUseCase
import com.kashapovrush.enter_code_feature.ui.StateEnterCodeScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterCodeViewModel @Inject constructor(
    private val signInWithCredentialUCUseCase: SignInWithCredentialUCUseCase
) : ViewModel() {

    private val _stateScreen = MutableSharedFlow<StateEnterCodeScreen>()
    val stateScreen = _stateScreen.asSharedFlow()
    fun signInWithCredential(code: String, id: String) {
        val credential = PhoneAuthProvider.getCredential(id, code)
        signInWithCredentialUCUseCase.signInWithCredentialUC(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("MainActivityTest", "success")
                    viewModelScope.launch {
                        _stateScreen.emit(StateEnterCodeScreen.Success)
                    }

                } else {
                    viewModelScope.launch {
                        Log.d("MainActivityTest", "error")
                        _stateScreen.emit(StateEnterCodeScreen.Error("error"))
                    }
                }
            }
    }
}