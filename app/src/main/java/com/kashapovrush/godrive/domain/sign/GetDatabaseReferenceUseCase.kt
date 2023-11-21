package com.kashapovrush.godrive.domain.sign

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class GetDatabaseReferenceUseCase @Inject constructor(private val repository: SignRepository) {

    operator fun invoke(): DatabaseReference {
        return repository.getDatabaseReference()
    }
}