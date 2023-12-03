package com.kashapovrush.godrive.domain.mainChat

import javax.inject.Inject

class LoggedInUserCounterUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(randomNumber: Long) {
        repository.loggedInUserCounter(randomNumber)
    }
}