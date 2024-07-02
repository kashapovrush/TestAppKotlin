package com.kashapovrush.sign_up_feature.ui

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kashapovrush.navigation.NavigationState
import com.kashapovrush.navigation.ScreenState
import com.kashapovrush.sign_up_feature.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(navigationState: NavigationState, viewModel: SignUpViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleText()

        Spacer(modifier = Modifier.height(16.dp))

        val inputNumberPhone = remember { mutableStateOf("") }

        OutlinedTextField(
            value = inputNumberPhone.value,
            textStyle = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            onValueChange = { newValue ->
                inputNumberPhone.value = newValue
            },
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .width(300.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { inputNumberPhone.value = "" }
                )
            },
            keyboardActions = (KeyboardActions(onPrevious = { inputNumberPhone.value = "" })),
            prefix = {
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "+7", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    VerticalDivider(thickness = 1.dp, modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        val isVisibleProgressBar = remember {
            mutableStateOf(false)
        }
        val state = viewModel.authState.collectAsState()
        val currentState = state.value

        when (currentState) {
            is State.Initial -> {
            }

            is State.Error -> {
                Toast.makeText(
                    context,
                    "Ошибка, попробуйте позже :(",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is State.SaveId -> {
                navigationState.navigateToEnterCodeScreen(currentState.id)
            }

            State.Success -> {
                navigationState.navigateTo(ScreenState.ROUTE_ENTER_CODE)
            }

            State.Loading -> {
                isVisibleProgressBar.value = true
            }
        }
        Button(
            onClick = {
                viewModel.phoneNumberVerification(
                    phoneNumber = inputNumberPhone.value,
                    activity = activity
                )

            },
            enabled = inputNumberPhone.value.length == 10,
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (isVisibleProgressBar.value) {
                CircularProgressIndicator(modifier = Modifier.size(28.dp), color = Color.White)
            } else {
                Text(
                    text = "Войти",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Войти без регистрации",
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = TextDecoration.Underline
        )

    }

}


@Composable
private fun TitleText() {
    Text(text = "Введите", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = "номер телефона для входа", style = MaterialTheme.typography.bodySmall)
}