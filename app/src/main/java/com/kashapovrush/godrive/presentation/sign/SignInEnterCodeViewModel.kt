package com.kashapovrush.godrive.presentation.sign

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DatabaseReference
import com.kashapovrush.godrive.domain.sign.GetDatabaseReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetUidUseCase
import com.kashapovrush.godrive.domain.sign.SignInWithCredentialUseCase
import javax.inject.Inject

class SignInEnterCodeViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val getUidUseCase: GetUidUseCase,
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase
): ViewModel() {

    fun signInWithCredentialUC(credential: PhoneAuthCredential): Task<AuthResult> {
        return signInWithCredentialUseCase.signInWithCredentialUC(credential)
    }

    fun getUid(): String {
        return getUidUseCase()
    }

    fun getDatabaseReference(): DatabaseReference {
        return getDatabaseReferenceUseCase()
    }
}