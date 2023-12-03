package com.kashapovrush.godrive.domain.mainChat

import javax.inject.Inject

class RemoveMessageAfterTimeUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(cityValue: String, time: Long){
        repository.removeMessageAfterTime(cityValue, time)
    }
}