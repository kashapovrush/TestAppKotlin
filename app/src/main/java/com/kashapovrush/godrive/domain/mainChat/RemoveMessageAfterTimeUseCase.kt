package com.kashapovrush.godrive.domain.mainChat

import javax.inject.Inject

class RemoveMessageAfterTimeUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(time: Long){
        repository.removeMessageAfterTime(time)
    }
}