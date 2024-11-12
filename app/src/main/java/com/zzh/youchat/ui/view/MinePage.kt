package com.zzh.youchat.ui.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zzh.youchat.R
import com.zzh.youchat.data.viewModel.UserViewModel
import com.zzh.youchat.ui.component.PersonalInfoCard
import com.zzh.youchat.ui.component.TableRow

@Composable
fun MinePage(
    onNavigateToSettings: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userViewModel: UserViewModel = hiltViewModel()
    val context = LocalContext.current
    val uid by userViewModel.uid.collectAsState()
    val TAG = "MinePage.kt"

    val nickname by userViewModel.nickname.collectAsState()
    val email by userViewModel.email.collectAsState()

    MinePageUI(
        onNavigateToSettings = { onNavigateToSettings() },
        onExit = {
            userViewModel.logout(uid) { isLogoutSuccess ->
                if (isLogoutSuccess) {
                    onNavigateToLogin()
                } else {
                    Toast.makeText(context, "退出登录失败", Toast.LENGTH_SHORT).show()
                }
            }
        },
        nickname = nickname,
        email = email,
        modifier = modifier
    )
    Log.d(TAG, "UserToken: ${userViewModel.userToken.collectAsState()}")

}

@Composable
fun MinePageUI(
    onNavigateToSettings: () -> Unit,
    onExit: () -> Unit,
    nickname: String,
    email: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                    .fillMaxWidth()
            ) {
                Row {
                    Box(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_more),
                            contentDescription = stringResource(R.string.more),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Box(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.mine),
                    style = MaterialTheme.typography.headlineMedium
                )
                Box(modifier = Modifier.height(20.dp))
                PersonalInfoCard(painterResource(R.drawable.avatar_example), nickname, email)
                Text(
                    text = stringResource(R.string.more),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 18.dp)
                )
            }
            TableRow(
                painter = painterResource(R.drawable.ic_settings),
                painterDescription = stringResource(R.string.settings),
                text = stringResource(R.string.settings),
                onClick = { onNavigateToSettings() }
            )
            TableRow(
                painter = painterResource(R.drawable.ic_about),
                painterDescription = stringResource(R.string.about),
                text = stringResource(R.string.about),
                onClick = { Toast.makeText(context, "还没写", Toast.LENGTH_SHORT).show() }
            )
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                onClick = { onExit() }
            ) {
                Text(stringResource(R.string.exit_login))
            }
        }

    }
}

@Composable
@Preview
fun MinePagePreview() {
    MinePageUI(
        {},
        {},
        "张三",
        "123123123",
        Modifier.fillMaxSize()
    )
}