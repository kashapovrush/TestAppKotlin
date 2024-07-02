package com.kashapovrush.sign_up_feature.ui

sealed class State {

    data object Initial: State()

    data object Loading: State()

    data object Success: State()

    data class Error(val error: String): State()

    data class SaveId(val id: String): State()
}