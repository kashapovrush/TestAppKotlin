package com.kashapovrush.godrive.domain.userData

import android.widget.AdapterView
import javax.inject.Inject

class SelectCityUseCase @Inject constructor(private val repository: UserDataRepository) {

    operator fun invoke(
        listCity: Array<String>,
        cityValue: String,
        state: Boolean,
        putCityValue: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener {
        return repository.selectCity(listCity, cityValue, state, putCityValue)
    }
}