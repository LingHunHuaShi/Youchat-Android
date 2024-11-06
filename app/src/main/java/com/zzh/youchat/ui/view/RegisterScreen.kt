package com.zzh.youchat.ui.view

import android.os.Build.VERSION.SDK_INT
import android.util.Base64
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
import com.zzh.youchat.data.viewModel.LoginViewModel
import com.zzh.youchat.data.viewModel.SettingsViewModel
import com.zzh.youchat.network.entity.responseDto.Captcha
import com.zzh.youchat.ui.component.LoginServerDialog
import dagger.hilt.android.EntryPointAccessors
import kotlin.random.Random

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = remember {
        EntryPointAccessors.fromApplication(context, ImageLoaderEntryPoint::class.java)
            .imageLoader()
    }

    val loginViewModel: LoginViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    var captcha = loginViewModel.captcha.observeAsState()

    RegisterScreenUI(
        onRegister = {},
        modifier = modifier,
        saveServerAddress = settingsViewModel::saveServerAddress,
        renewApi = loginViewModel::renewApi,
        imageLoader = imageLoader,
        captcha = captcha.value
    )
}

@Composable
fun RegisterScreenUI(
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
    saveServerAddress: (String) -> Unit,
    renewApi: () -> Unit,
    imageLoader: ImageLoader,
    captcha: Captcha?,
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var captchaCode by remember { mutableStateOf("") }
    var passwordCorrespond by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

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
                text = stringResource(R.string.register),
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
                value = nickname,
                onValueChange = {
                    nickname = it.replace(" ", "")
                },
                label = { Text(text = stringResource(R.string.nickname)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it.replace(" ", "")
                    passwordCorrespond = (passwordConfirm == password)
                },
                label = { Text(text = stringResource(R.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = passwordConfirm,
                onValueChange = {
                    passwordConfirm = it.replace(" ", "")
                    passwordCorrespond = (passwordConfirm == password)
                },
                label = { Text(text = stringResource(R.string.confirm_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                singleLine = true,
                isError = passwordCorrespond
            )

            // 验证码
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                OutlinedTextField(
                    value = captchaCode,
                    onValueChange = {
                        captchaCode = it.replace(" ", "")
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

            Row {
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
//                        val loginRequest =
//                            LoginRequest(email, password, captcha.captchaImgUuid, captchaCode)
//                        onLogin(loginRequest)
                        onRegister()
                    } else {
                        Toast.makeText(context, "请先获取验证码", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Text(stringResource(R.string.register), style = TextStyle(fontSize = 16.sp))
            }
        }

        if (showDialog) {
            LoginServerDialog(
                onConfirm = { address ->
                    saveServerAddress(address)
                    renewApi()
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }

    }
}

@Preview
@Composable
fun RegisterScreenUiPreview() {
    val captchaExp = Captcha(
        "25c0b294-22e5-4a53-bc61-054764363a48",
        stringResource(R.string.example_captcha_base64)
    )
    RegisterScreenUI(
        onRegister = {},
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        saveServerAddress = {},
        renewApi = {},
        captcha = captchaExp,
        imageLoader = LocalContext.current.imageLoader,
    )
}