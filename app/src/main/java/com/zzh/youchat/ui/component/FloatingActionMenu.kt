package com.zzh.youchat.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh.youchat.R

@Composable
fun FloatingActionMenu() {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.wrapContentSize()
            .padding(16.dp)
    ) {
        // Dropdown menu
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = isMenuExpanded,
            ) {
                Text("fjfjfjfjfj")
            }
        }

        FloatingActionButton(
            onClick = {
                isMenuExpanded = !isMenuExpanded
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(painter = painterResource(R.drawable.ic_add), contentDescription = stringResource(R.string.menu_button))
        }
    }
}

@Preview
@Composable
fun FloatingActionMenuPreview() {
    FloatingActionMenu()
}