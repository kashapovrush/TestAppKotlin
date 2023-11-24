package com.kashapovrush.godrive.domain.mainChat

import javax.inject.Inject

class SetCityValue @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(listOfCities: Array<String>, city: String, position: Int, state: Boolean) {
        repository.setCityValue(listOfCities, city, position, state)
    }
}