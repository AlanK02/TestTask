package com.alan.testtask.presentation.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alan.testtask.domain.entity.ChatInfo

@Composable
fun ChatsScreen(onChatSelected: (ChatInfo) -> Unit, modifier: Modifier = Modifier) {
    val chats = listOf("Chat 1", "Chat 2", "Chat 3")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        chats.forEach { chat ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onChatSelected(ChatInfo(chat)) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = chat,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
