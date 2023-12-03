package com.kashapovrush.godrive.domain.mainChat

import javax.inject.Inject

class GetMessageKeyUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(): String {
        return repository.getMessageKey()
    }
}