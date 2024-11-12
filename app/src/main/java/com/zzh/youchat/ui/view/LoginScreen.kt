package com.zzh.youchat.ui.view

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
import com.zzh.youchat.data.viewModel.UserViewModel
import com.zzh.youchat.data.viewModel.SettingsViewModel
import com.zzh.youchat.exception.YouChatDataException
import com.zzh.youchat.network.entity.requestDto.LoginRequest
import com.zzh.youchat.network.entity.responseDto.Captcha
import com.zzh.youchat.ui.component.LoginServerDialog
import dagger.hilt.android.EntryPointAccessors
import kotlin.random.Random


@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val TAG = "Login Screen Debug"

    val userViewModel: UserViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val context = LocalContext.current
    val imageLoader = remember {
        EntryPointAccessors.fromApplication(context, ImageLoaderEntryPoint::class.java)
            .imageLoader()
    }

    val serverAddress by settingsViewModel.serverAddress.collectAsState()

    // 使用 LaunchedEffect 来监听 serverAddress 的变化
    LaunchedEffect(serverAddress) {
        userViewModel.renewApi()
        Log.d(TAG, "LoginScreen-server: $serverAddress")
        if (serverAddress != "_INIT_ADDRESS_VALUE_") {
            if (serverAddress.startsWith("http://") || serverAddress.startsWith("https://")) {
                Log.d(TAG, "LoginScreen: 填写正确")
                userViewModel.fetchCaptcha()
            } else {
                Toast.makeText(context, "请先检查登录服务器设置", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val captcha = userViewModel.captcha.observeAsState()
    val errMsg by userViewModel.errMsg.observeAsState()

    errMsg?.let { message ->
        if (message.isNotEmpty()) {
//            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            // 清空错误消息以避免重复显示
            Log.e(TAG, "LoginScreen: $message")
            userViewModel.clearErrMsg()
        }
    }

    LoginScreenUI(
        onLogin = { request ->
            if (request.email.isEmpty() || request.password.isEmpty() || request.captchaCode.isEmpty() || request.captchaImgUuid.isEmpty()) {
                throw YouChatDataException("输入的项不完整，请检查")
            }
            userViewModel.login(request) { loginResponse ->
                if (loginResponse == null) {
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.saveUserToken(loginResponse.token)
                    userViewModel.saveUid(loginResponse.uid)
                    userViewModel.saveEmail(loginResponse.email)
                    userViewModel.saveNickname(loginResponse.nickname)
                    userViewModel.saveIsAdmin(loginResponse.isAdmin)
                    onNavigateToMain()
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = modifier,
        saveServerAddress = settingsViewModel::saveServerAddress,
        renewApi = userViewModel::renewApi,
        imageLoader = imageLoader,
        captcha = captcha.value,
    )
}


@Composable
fun LoginScreenUI(
    onLogin: (loginRequest: LoginRequest) -> Unit,
    modifier: Modifier = Modifier,
    saveServerAddress: (String) -> Unit,
    renewApi: () -> Unit,
    imageLoader: ImageLoader,
    captcha: Captcha?,
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
                    email = it.replace(" ", "")
                },
                label = { Text(text = stringResource(R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it.replace(" ", "")
                },
                label = { Text(text = stringResource(R.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true
            )

            // 验证码
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                OutlinedTextField(
                    value = captchaCode,
                    onValueChange = {
                        captchaCode = it
                    },
                    label = { Text(stringResource(R.string.captcha)) },

                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                )
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(gifBase64Bytes)
                        .decoderFactory(
                            if (SDK_INT >= 28) ImageDecoderDecoder.Factory()
                            else GifDecoder.Factory()
                        )
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
                    if (captcha != null) {
                        val loginRequest =
                            LoginRequest(email, password, captcha.captchaImgUuid, captchaCode)
                        try {
                            onLogin(loginRequest)
                        } catch (e: YouChatDataException) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "Login Error: " + e.message)
                        } catch (e: Exception) {
                            Log.e(TAG, "Login Error:" + e.message)
                        }
                    } else {
                        Toast.makeText(context, "请先获取验证码", Toast.LENGTH_SHORT).show()
                    }
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
                    renewApi()
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
    val captchaExp = Captcha(
        "25c0b294-22e5-4a53-bc61-054764363a48",
        stringResource(R.string.example_captcha_base64)
    )

    LoginScreenUI(
        onLogin = {},
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        saveServerAddress = {},
        renewApi = {},
        imageLoader = LocalContext.current.imageLoader,
        captcha = captchaExp,
    )

}