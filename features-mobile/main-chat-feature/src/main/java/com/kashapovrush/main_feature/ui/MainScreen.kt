package com.kashapovrush.main_feature.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        TopBar()

        val scrollLazyColumnState = rememberLazyListState()

        LazyColumn(
            state = scrollLazyColumnState,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()

        ) {
            items(3) {
                ReceiverTextMessage()
            }
        }

        val messageState = remember { mutableStateOf("") }
        val bottomNavigationState = remember { mutableStateOf(false) }
        val visibleState =
            animateFloatAsState(
                targetValue = if (!bottomNavigationState.value) 1f else 0f,
                animationSpec = tween(1000)
            )
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(2.dp)
                    .clickable { bottomNavigationState.value = !bottomNavigationState.value },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = if (bottomNavigationState.value) Icons.Default.Message else Icons.Default.Menu,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (!bottomNavigationState.value) {
                InputTextMessage(messageState = messageState, visibleState = visibleState)
            } else {
                AnimatedMenu()
            }
        }
    }
}

@Composable
fun AnimatedMenu() {
    val stateAnimation = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = stateAnimation,
        enter = fadeIn(animationSpec = tween(500)) + slideIn(
            animationSpec = tween(500),
            initialOffset = { IntOffset(it.width, 0) }),
        exit = fadeOut(animationSpec = tween(500)) + shrinkOut(
            animationSpec = tween(500),
        )
    ) {
        Menu()
    }
}

@Preview
@Composable
fun Menu() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        IconInCircle(image = Icons.Default.PeopleAlt)
        IconInCircle(image = Icons.Default.Settings)
        IconInCircle(image = Icons.Default.Output)
    }
}

@Composable
fun IconInCircle(image: ImageVector) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = image,
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun InputTextMessage(messageState: MutableState<String>, visibleState: State<Float>) {
    BasicTextField(
        value = messageState.value, onValueChange = { newValue ->
            messageState.value = newValue
        }, modifier = Modifier
            .alpha(visibleState.value)
            .width(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 8.dp)
            ,
        cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            if (messageState.value.isEmpty()) {
                Text(
                    text = "Сообщение",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            } else {
                innerTextField()
            }
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            color = Color.White
        )
    )

    Box(
        modifier = Modifier
            .alpha(visibleState.value)
            .height(60.dp)
            .width(60.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(8.dp)
            ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (messageState.value.isEmpty()) Icons.Default.KeyboardVoice else Icons.Default.Send,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = Color.White
        )

    }
}

@Composable
private fun TopBar() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PeopleAlt,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(36.dp)
                .align(Alignment.CenterEnd)

        )
        Text(
            text = "Выберите город",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MessageItem() {

}
