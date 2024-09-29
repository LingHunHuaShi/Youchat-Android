package com.zzh.youchat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B72C0),
    primaryContainer = Color(0xFFD3E4FF),
    onPrimaryContainer = Color(0xFF001C38),
    surface = Color(0xFFF3F4F9),
    secondary = Color(0xFFEFF1F8),
    background = Color(0xFFFCFCFF),
    onSurfaceVariant = Color(0xFF001E2F),
    onSurface = Color(0xFF44474E),
    inverseOnSurface = Color(0xFF74777F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA2C9FF),
    primaryContainer = Color(0xFF2F4156),
    onPrimaryContainer = Color(0xFFD7E2FF),
    surface = Color(0xFF13232C),
    secondary = Color(0xFF1E2A32),
    background = Color(0xFF0E181E),
    onSurfaceVariant = Color(0xFFE0F1FF),
    onSurface = Color(0xFFBFC6DA),
    inverseOnSurface = Color(0xFFA8ADBD)
)

@Composable
fun YouChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}