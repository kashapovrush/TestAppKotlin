package com.kashapovrush.enter_code_feature.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kashapovrush.enter_code_feature.viewmodel.EnterCodeViewModel
import com.kashapovrush.navigation.NavigationState
import com.kashapovrush.navigation.ScreenState

@Composable
fun EnterCodeScreen(navigationState: NavigationState, viewModel: EnterCodeViewModel, id: String) {
    Log.d("MainActivityTest", "recomposition")
    val context = LocalContext.current
    val inputCode = remember { mutableStateOf("") }

    val state = viewModel.stateScreen.collectAsState(StateEnterCodeScreen.Initial)
    val currentScreen = state.value

    SetFunc(currentScreen, context, navigationState, inputCode, viewModel, id)

}

@Composable
private fun SetFunc(
    currentScreen: StateEnterCodeScreen,
    context: Context,
    navigationState: NavigationState,
    inputCode: MutableState<String>,
    viewModel: EnterCodeViewModel,
    id: String
) {
    when (currentScreen) {

        is StateEnterCodeScreen.Error -> {
            Toast.makeText(
                context,
                currentScreen.error,
                Toast.LENGTH_SHORT
            ).show()
        }

        StateEnterCodeScreen.Initial -> {}
        StateEnterCodeScreen.Success -> {
            navigationState.navigateTo(ScreenState.MainChatScreen.route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleText()

        Spacer(modifier = Modifier.height(16.dp))

        SetEditText(inputCode)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signInWithCredential(inputCode.value, id)
            },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = inputCode.value.length == 6
        ) {
            Text(
                text = "Потвердить",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(48.dp))


    }
}

@Composable
private fun SetEditText(inputCode: MutableState<String>) {
    OutlinedTextField(
        value = inputCode.value,
        maxLines = 1,
        onValueChange = { newValue ->
            inputCode.value = newValue
        },
        modifier = Modifier
            .padding(16.dp)
            .width(300.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { inputCode.value = "" }
            )

        }
    )
}


@Composable
private fun TitleText() {
    Text(text = "Потвердите", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = "номер телефона", style = MaterialTheme.typography.bodySmall)
}