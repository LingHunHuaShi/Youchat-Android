package com.zzh.youchat.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zzh.youchat.ui.component.ChatRow
import com.zzh.youchat.R

@Composable
fun ChatPage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        LazyColumn {
            items(10) { index ->
                ChatRow(
                    avatar = painterResource(R.drawable.gezhenjiang_avatar),
                    "葛振江",
                    "我是卷比",
                    "1:23 PM",
                    "99+"
                )
            }
        }
    }
}

@Composable
@Preview
fun ChatPagePreview() {
    ChatPage(Modifier.fillMaxSize())
}