package com.kashapovrush.godrive.domain.sign

import javax.inject.Inject

class GetUidUseCase @Inject constructor(private val repository: SignRepository) {

    operator fun invoke(): String {
        return repository.getUid()
    }
}