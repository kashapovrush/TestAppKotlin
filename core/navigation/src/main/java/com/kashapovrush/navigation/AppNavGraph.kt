package com.kashapovrush.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    signUpContent: @Composable () -> Unit,
    enterCodeContent: @Composable (String) -> Unit,
    freeChatContent: @Composable () -> Unit,
    mainChatContent: @Composable () -> Unit,
    profileContent: @Composable () -> Unit,
    profileFreeContent: @Composable () -> Unit,
    settingsContent: @Composable () -> Unit
    ) {

    NavHost(navController = navHostController, startDestination = ScreenState.SignUpScreen.route) {

        composable(ScreenState.SignUpScreen.route) {
            signUpContent()
        }

        composable(ScreenState.EnterCodeScreen.route) {
            val id = it.arguments?.getString("id") ?: ""
            enterCodeContent(id)
        }

        composable(ScreenState.FreeChatScreen.route) {
            freeChatContent()
        }

        composable(ScreenState.MainChatScreen.route) {
            mainChatContent()
        }

        composable(ScreenState.ProfileScreen.route) {
            profileContent()
        }

        composable(ScreenState.ProfileFreeScreen.route) {
            profileFreeContent()
        }

        composable(ScreenState.SettingsScreen.route) {
            settingsContent()
        }
    }

}
