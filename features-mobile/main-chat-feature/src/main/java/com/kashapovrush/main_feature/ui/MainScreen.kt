package com.kashapovrush.main_feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
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
            item {
                ReceiverVoiceMessage()
            }
            item {
                ReceiverTextMessage()
            }

            item { SendTextMessage() }
            item { SendVoiceMessage() }
            item {
                ReceiverVoiceMessage()
            }
            item {
                ReceiverTextMessage()
            }
            items(3) {
                ReceiverTextMessage()
            }
        }

        val messageState = remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

//            OutlinedTextField(value = messageState.value, onValueChange = {
//                messageState.value = it
//            })

            BasicTextField(
                value = messageState.value, onValueChange = { newValue ->
                    messageState.value = newValue
                }, modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (messageState.value.isEmpty()) {
                        Text(
                            text = "Сообщение",
                            color = Color.White,
                            fontSize = 18.sp
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
                    .height(60.dp)
                    .width(60.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (messageState.value.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.KeyboardVoice,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBar() {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconWithText(Icons.Default.PeopleAlt, "Профиль")
        IconWithText(Icons.Default.ChatBubbleOutline, "Чат")
        IconWithText(Icons.Default.Settings, "Настройки")
    }
}

@Composable
private fun IconWithText(image: ImageVector, text: String) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = image,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
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

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart)
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

@Composable
private fun ReceiverTextMessage() {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 60.dp, top = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            imageVector = Icons.Default.PeopleAlt,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .background(Color.White)

        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Стоят возле рамазана sdfsf sk kjdfsdfs sdfsf sdfsdf sdfsdf ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }

}

@Composable
private fun ReceiverVoiceMessage() {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 60.dp, top = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            imageVector = Icons.Default.PeopleAlt,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .background(Color.White)

        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }

}

@Composable
private fun SendTextMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, top = 4.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Стоят возле рамазана",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.PeopleAlt,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 1.dp, MaterialTheme.colorScheme.error, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))
    }

}

@Composable
private fun SendVoiceMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, top = 4.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.PeopleAlt,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 1.dp, MaterialTheme.colorScheme.error, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}
