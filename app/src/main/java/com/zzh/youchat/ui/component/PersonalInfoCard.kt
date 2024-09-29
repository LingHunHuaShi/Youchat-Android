package com.zzh.youchat.ui.component

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh.youchat.R

@Composable
fun PersonalInfoCard(avatar: Painter, nickname: String, phoneNumber: String) {
    Surface (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = avatar,
                contentDescription = stringResource(R.string.user_avatar),
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
            Box(modifier = Modifier.width(16.dp))
            Column (
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(56.dp)
            ) {
                Text(nickname, style = MaterialTheme.typography.titleMedium)
                Text(phoneNumber, style = MaterialTheme.typography.bodyMedium)
            }
            Box(modifier = Modifier.weight(1f))
            FilledTonalButton(
                onClick = {},
                modifier = Modifier.height(40.dp)
            ) {
                Text(stringResource(R.string.edit), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
@Preview
fun PersonalInfoCardPreview() {
    PersonalInfoCard(painterResource(R.drawable.avatar_example), "张三", "12312312312")
}