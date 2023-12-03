package com.kashapovrush.godrive.domain.mainChat

import android.widget.EditText
import javax.inject.Inject

class ShowButtonVoiceUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(editText: EditText, afterTextChanged: () -> Unit) {
        repository.showButtonVoice(editText, afterTextChanged)
    }
}