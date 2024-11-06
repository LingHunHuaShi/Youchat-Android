package com.zzh.youchat.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsPage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "THIS IS SETTINGS PAGE",
            style = MaterialTheme.typography.displayLarge,
        )
    }
}

@Composable
@Preview
fun SettingsPagePreview() {
    SettingsPage()
}