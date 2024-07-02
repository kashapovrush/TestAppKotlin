package com.kashapovrush.profile_feature.viewmodel

import androidx.lifecycle.ViewModel
import com.kashapovrush.profile_api.SelectedCityUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val selectedCityUseCase: SelectedCityUseCase
): ViewModel() {

    fun selectedCity(city: String) {
        selectedCityUseCase.selectedCity(city)
    }
}