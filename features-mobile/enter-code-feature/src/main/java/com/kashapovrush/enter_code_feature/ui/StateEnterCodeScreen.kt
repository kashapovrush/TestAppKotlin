package com.kashapovrush.enter_code_feature.ui

sealed class StateEnterCodeScreen {

    data object Initial: StateEnterCodeScreen()

    data class Error(val error: String): StateEnterCodeScreen()

    data object Success: StateEnterCodeScreen()
}