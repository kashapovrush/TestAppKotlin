package com.kashapovrush.godrive.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kashapovrush.chat_feature.FreeChatScreen
import com.kashapovrush.enter_code_feature.ui.EnterCodeScreen
import com.kashapovrush.main_feature.ui.MainScreen
import com.kashapovrush.palette.GoDriveKotlinTheme
import com.kashapovrush.profile_feature.ui.ProfileScreen
import com.kashapovrush.profile_free_feature.ProfileFreeScreen
import com.kashapovrush.settings_feature.SettingScreen
import com.kashapovrush.sign_up_feature.ui.SignUpScreen
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoDriveKotlinTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingScreen()
                }
            }
        }
    }
}
