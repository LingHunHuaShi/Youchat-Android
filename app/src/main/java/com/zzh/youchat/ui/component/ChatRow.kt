package com.zzh.youchat.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.youchat.R


@Composable
fun ChatRow(
    avatar: Painter,
    nickname: String,
    lastMessage: String,
    timeOfLastMessage: String,
    numberOfUnreadMessage: String, // "0" for no unread message
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 16.dp)
    ) {
        Row {
            Image(
                painter = avatar,
                contentDescription = stringResource(R.string.user_avatar),
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Box(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = nickname,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = timeOfLastMessage,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Box(modifier = Modifier.height(8.dp))
                if (numberOfUnreadMessage != "0") {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1974E6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = numberOfUnreadMessage,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun ChatRowPreview() {
    ChatRow(
        painterResource(R.drawable.gezhenjiang_avatar),
        "葛振江",
        "休息个蛋",
        "2:49 PM",
        "1"
    )
}