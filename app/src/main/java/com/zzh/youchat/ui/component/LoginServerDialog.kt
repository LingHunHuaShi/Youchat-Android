package com.zzh.youchat.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.zzh.youchat.R
import com.zzh.youchat.data.viewModel.SettingsViewModel

@Composable
fun LoginServerDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val storedAddress = settingsViewModel.serverAddress.collectAsState().value
    var address by remember { mutableStateOf(storedAddress) }
    val TAG = "Login Server Dialog"
    Log.d(TAG, "LoginServerDialog: address: $address")
    LoginServerDialogUI(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        address = address,
        onValueChange = {
            address = it
        })
}

@Composable
private fun LoginServerDialogUI(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    address: String,
    onValueChange: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "设置登录服务器",
                    style = MaterialTheme.typography.titleMedium,
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { onValueChange(it) },
                    modifier = Modifier.padding(top = 24.dp),
                    label = {
                        Text("e.g. https://...")
                    },
                    singleLine = true,
                )
                Box(modifier = Modifier.weight(1f))
                Row {
                    Box(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { onConfirm(address) },
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                    TextButton(
                        onClick = { onDismiss() },
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginServerDialogPreview() {
    LoginServerDialogUI({}, {}, "example", {})
}