package com.kashapovrush.godrive.domain.mainChat

import com.kashapovrush.godrive.domain.models.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(): User {
        return User()
    }
}