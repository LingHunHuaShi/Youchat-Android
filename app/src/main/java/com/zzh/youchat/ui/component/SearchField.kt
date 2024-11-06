package com.zzh.youchat.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh.youchat.R


@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    searchIcon: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .height(64.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.search),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
            },
            shape = RoundedCornerShape(32.dp),
            leadingIcon = {
                Icon(painter = painterResource(searchIcon), contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color(0x00000000),
                unfocusedIndicatorColor = Color(0x00000000),
            ),
            modifier = Modifier.background(Color(0x00000000)),
            singleLine = true
        )
    }
}

@Preview
@Composable
fun SearchFieldPreview() {
    SearchField(
        "",
        {},
        R.drawable.ic_search
    )
}