package com.zzh.youchat.ui.component

import android.widget.TableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh.youchat.R

@Composable
fun TableRow(painter: Painter? = null, painterDescription: String? = null, text: String, onClick: ()->Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Box(modifier = Modifier.size(16.dp))
        if (painter != null) {
            Icon(
                painter = painter,
                contentDescription = painterDescription,
                modifier = Modifier.size(24.dp)
            )
            Box(modifier = Modifier.size(16.dp))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
@Preview
fun TableRowPreview() {
    TableRow(painterResource(R.drawable.ic_settings), "设置", "设置", {})
}