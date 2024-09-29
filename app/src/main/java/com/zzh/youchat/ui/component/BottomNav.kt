package com.zzh.youchat.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.zzh.youchat.R

@Composable
fun BottomNav(itemList: List<String>, iconList: List<@Composable () -> Unit>, selectedItem: MutableIntState, modifier:Modifier = Modifier) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
    ) {
        itemList.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { iconList[index]() },
                selected = selectedItem.intValue == index,
                label = { Text(item) },
                onClick = {
                    selectedItem.intValue = index
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Preview
@Composable
fun BottomNavPreview() {
    val itemList = listOf(
        stringResource(R.string.chat),
        stringResource(R.string.contact),
        stringResource(R.string.mine)
    )

    val iconList: List<@Composable ()->Unit> = listOf(
        {
            Icon(
                painter = painterResource(R.drawable.ic_chat),
                contentDescription = stringResource(R.string.chat)
            )
        },
        {
            Icon(
                painter = painterResource(R.drawable.ic_contact),
                contentDescription = stringResource(R.string.contact)
            )
        },
        {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = stringResource(R.string.mine)
            )
        },
    )

    val selectedItem = remember { mutableIntStateOf(0) }

    BottomNav(itemList, iconList, selectedItem)
}
