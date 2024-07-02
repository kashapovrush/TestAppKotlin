package com.kashapovrush.profile_api

import javax.inject.Inject

class SelectedCityUseCase @Inject constructor(private val repository: ProfileRepository) {

    fun selectedCity(city: String) {
        repository.selectedCity(city)
    }
}