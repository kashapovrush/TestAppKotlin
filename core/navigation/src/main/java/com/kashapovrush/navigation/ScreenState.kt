package com.kashapovrush.navigation

sealed class ScreenState(val route: String) {


    data object EnterCodeScreen: ScreenState(ROUTE_ENTER_CODE)
    data object FreeChatScreen: ScreenState(ROUTE_FREE_CHAT)
    data object MainChatScreen: ScreenState(ROUTE_MAIN_CHAT)
    data object ProfileScreen: ScreenState(ROUTE_PROFILE)
    data object ProfileFreeScreen: ScreenState(ROUTE_PROFILE_FREE)
    data object SettingsScreen: ScreenState(ROUTE_SETTINGS)
    data object SignUpScreen: ScreenState(ROUTE_SIGN_UP)



    companion object {

        const val ROUTE_ENTER_CODE = "enter_code"
        const val ROUTE_FREE_CHAT = "free_chat"
        const val ROUTE_MAIN_CHAT = "main_chat"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_PROFILE_FREE = "profile_free"
        const val ROUTE_SETTINGS = "settings"
        const val ROUTE_SIGN_UP = "sign_up"
    }
}
