package com.kashapovrush.godrive.domain.sign

import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class GetStorageReferenceUseCase @Inject constructor(private val repository: SignRepository) {

    operator fun invoke(): StorageReference {
        return repository.getStorageReference()
    }
}