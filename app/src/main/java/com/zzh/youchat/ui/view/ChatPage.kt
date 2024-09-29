package com.zzh.youchat.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatPage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Text("Chat Page")
    }
}

@Composable
@Preview
fun ChatPagePreview() {
    ChatPage(Modifier.fillMaxSize())
}