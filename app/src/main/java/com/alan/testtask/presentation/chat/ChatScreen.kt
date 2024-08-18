package com.alan.testtask.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alan.testtask.R
import com.alan.testtask.domain.entity.ChatInfo


@Composable
fun ChatScreen(chatInfo: ChatInfo, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(
                "Hello!" to true,
                "How are you?" to false,
                "I'm fine, thanks!" to true
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { (message, isUserMessage) ->
                ChatBubble(
                    message = message,
                    isMine = isUserMessage,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                placeholder = { Text(text = stringResource(R.string.enter_your_message)) },
                modifier = Modifier
                    .weight(1f)
                    .imePadding()
            )

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        messages = messages + (text to true)
                        text = ""
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(R.string.send),
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun ChatBubble(
    message: String,
    isMine: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        val bubbleShape = if (isMine) {
            RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
        } else {
            RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
        }

        val backgroundColor = if (isMine) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surface
        }

        val textColor = if (isMine) {
            Color.White
        } else {
            MaterialTheme.colorScheme.onSurface
        }

        Card(
            shape = bubbleShape,
            colors = CardColors(
                containerColor = backgroundColor,
                contentColor = Color.White,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = backgroundColor
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
