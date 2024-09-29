package com.zzh.youchat.ui.view

import android.content.Context
import android.graphics.Paint.Cap
import android.os.Build.VERSION.SDK_INT
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.imageLoader
import coil.request.ImageRequest
import com.zzh.youchat.R
import com.zzh.youchat.data.ImageLoaderEntryPoint
import com.zzh.youchat.data.entity.Captcha
import com.zzh.youchat.data.viewModel.LoginViewModel
import com.zzh.youchat.data.viewModel.SettingsViewModel
import com.zzh.youchat.ui.component.LoginServerDialog
import dagger.hilt.android.EntryPointAccessors
import kotlin.random.Random


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val TAG = "Login Screen Debug"

    val loginViewModel: LoginViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val context = LocalContext.current
    val imageLoader = remember {
        EntryPointAccessors.fromApplication(context, ImageLoaderEntryPoint::class.java).imageLoader()
    }

    val serverAddress by settingsViewModel.serverAddress.collectAsState()

    Log.d(TAG, "LoginScreen88888: ${"http://192.168.101.146".startsWith("http://")}")

    // 使用 LaunchedEffect 来监听 serverAddress 的变化
    LaunchedEffect(serverAddress) {
        Log.d(TAG, "LoginScreen-server: $serverAddress")

        if (serverAddress.startsWith("http://")) {
            Log.d(TAG, "LoginScreen: 填写正确")
            loginViewModel.fetchCaptcha(context)
        } else {
            Toast.makeText(context, "请先检查登录服务器设置", Toast.LENGTH_SHORT).show()
        }
    }

    val captcha = loginViewModel.captcha.observeAsState()
    val errMsg = loginViewModel.errMsg.observeAsState()

    LoginScreenUI(
        onLoginSuccess = onLoginSuccess,
        modifier = modifier,
        saveServerAddress = settingsViewModel::saveServerAddress,
        imageLoader = imageLoader,
        captcha = captcha.value,
        errMsg = errMsg.value,
    )
}


@Composable
fun LoginScreenUI(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    saveServerAddress: (String) -> Unit,
    imageLoader: ImageLoader,
    captcha: Captcha?,
    errMsg: String?,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var captchaCode by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val TAG = "Login Screen Debug"

    Log.d(TAG, "LoginScreenUI: $captcha")

    val gifBase64Bytes = if (captcha != null) {
        Base64.decode(captcha.captchaImg.substring(22), Base64.DEFAULT)
    } else {
        // 生成一个随机的 ByteArray，长度为 10（可以根据需要调整）
        ByteArray(10).apply { Random.nextBytes(this) }
    }



    Surface(
        modifier = modifier
    ) {
        Column {
            Text(
                text = stringResource(R.string.login_welcome),
                style = TextStyle(
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 150.dp, start = 10.dp)
            )

            // 邮箱密码输入框
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text(text = stringResource(R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = stringResource(R.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            // 验证码
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ){
                OutlinedTextField(
                    value = captchaCode,
                    onValueChange = {
                        captchaCode = it
                    },
                    label = { Text(stringResource(R.string.captcha))},

                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                )
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(gifBase64Bytes)
                        .decoderFactory(if (SDK_INT >= 28) ImageDecoderDecoder.Factory()
                         else GifDecoder.Factory())
                        .build(),
                    contentDescription = "captcha",
                    imageLoader = imageLoader,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp, end = 10.dp)
                )
            }

            // 注册账号与设置登录服务器
            Row {
                Text(
                    text = "没有账号？点此注册",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clickable {
                            Toast
                                .makeText(context, "register", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
                Box(modifier = Modifier.weight(1f))
                Text(
                    text = "设置登录服务器",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clickable {
                            showDialog = true
                        }
                )
            }
            FilledTonalButton(
                onClick = {
                    Toast.makeText(context, "模拟登录测试", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Text("登录", style = TextStyle(fontSize = 16.sp))
            }
        }

        if (showDialog) {
            LoginServerDialog(
                onConfirm = { address ->
                    saveServerAddress(address)
                    Log.d(TAG, "LoginScreen: $address")
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}



@Composable
@Preview
fun LoginScreenPreview() {
    val captchaExp = Captcha("sdsd", "Rxs")

    LoginScreenUI(
        onLoginSuccess = {},
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        saveServerAddress = {},
        imageLoader = LocalContext.current.imageLoader,
        captcha = captchaExp,
        errMsg = null,
    )

}