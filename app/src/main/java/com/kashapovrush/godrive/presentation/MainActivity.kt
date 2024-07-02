package com.kashapovrush.godrive.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashapovrush.chat_feature.FreeChatScreen
import com.kashapovrush.enter_code_feature.ui.EnterCodeScreen
import com.kashapovrush.enter_code_feature.viewmodel.EnterCodeViewModel
import com.kashapovrush.main_feature.ui.MainScreen
import com.kashapovrush.navigation.AppNavGraph
import com.kashapovrush.navigation.rememberNavigationState
import com.kashapovrush.palette.GoDriveKotlinTheme
import com.kashapovrush.profile_feature.ui.ProfileScreen
import com.kashapovrush.profile_feature.viewmodel.ProfileViewModel
import com.kashapovrush.profile_free_feature.ProfileFreeScreen
import com.kashapovrush.settings_feature.ui.SettingScreen
import com.kashapovrush.settings_feature.viewmodel.SettingsViewModel
import com.kashapovrush.sign_up_feature.ui.SignUpScreen
import com.kashapovrush.sign_up_feature.viewmodel.SignUpViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val component by lazy {
        (application as ApplicationDrive).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {

            val settingsViewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
            val profileViewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
            val signUpViewModel: SignUpViewModel = viewModel(factory = viewModelFactory)
            val enterCodeViewModel: EnterCodeViewModel = viewModel(factory = viewModelFactory)
            val navigationState = rememberNavigationState()

            GoDriveKotlinTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavGraph(
                        navHostController = navigationState.navHostController,
                        signUpContent = {
                            SignUpScreen(
                                navigationState = navigationState,
                                viewModel = signUpViewModel
                            )
                        },
                        enterCodeContent = {id ->
                            EnterCodeScreen(
                                navigationState = navigationState,
                                viewModel = enterCodeViewModel,
                                id = id
                            )
                        },
                        freeChatContent = { FreeChatScreen() },
                        mainChatContent = { MainScreen(navigationState = navigationState) },
                        profileContent = { ProfileScreen(viewModel = profileViewModel) },
                        profileFreeContent = { ProfileFreeScreen() },
                        settingsContent = { SettingScreen(viewModel = settingsViewModel) })

                }
            }
        }
    }
}
