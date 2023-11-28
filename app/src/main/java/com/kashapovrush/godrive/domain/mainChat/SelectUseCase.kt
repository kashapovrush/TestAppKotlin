package com.kashapovrush.godrive.domain.mainChat

import android.widget.AdapterView
import java.text.FieldPosition
import javax.inject.Inject

class SelectUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(
        listCity: Array<String>,
        cityValue: String,
        state: Boolean,
        putCityValue: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener {
        return repository.select(listCity, cityValue, state, putCityValue)
    }
}