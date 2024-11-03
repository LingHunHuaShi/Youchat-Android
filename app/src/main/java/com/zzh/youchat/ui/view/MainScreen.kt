package com.zzh.youchat.ui.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zzh.youchat.R
import com.zzh.youchat.ui.component.BottomNav
import com.zzh.youchat.ui.component.FloatingActionMenu

@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToLogin: () -> Unit,
    saveLoginStatus: (Boolean) -> Unit,
    modifier: Modifier = Modifier) {

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

    val selectedPage = remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedContent(
                targetState = selectedPage.intValue,
                label = "page switch animation"
            ) { targetValue ->
                when (targetValue) {
                    0 -> ChatPage()
                    1 -> ContactPage()
                    2 -> MinePage(
                        onNavigateToSettings = {
                            onNavigateToSettings()
                        },
                        onNavigateToLogin = {
                            saveLoginStatus(false)
                            onNavigateToLogin()
                        }
                    )
                }
            }
            FloatingActionMenu()
        }
        BottomNav(itemList = itemList, iconList = iconList, selectedPage)
    }

}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen({}, {}, {})
}