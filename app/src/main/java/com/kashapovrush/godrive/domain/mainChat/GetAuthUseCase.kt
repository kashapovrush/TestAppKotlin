package com.kashapovrush.godrive.domain.mainChat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(): FirebaseAuth {
        return repository.getAuth()
    }
}