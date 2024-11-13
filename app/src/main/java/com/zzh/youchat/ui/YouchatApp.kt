package com.zzh.youchat.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zzh.youchat.data.viewModel.UserViewModel
import com.zzh.youchat.data.viewModel.SettingsViewModel
import com.zzh.youchat.ui.view.LoginScreen
import com.zzh.youchat.ui.view.MainScreen
import com.zzh.youchat.ui.view.RegisterScreen
import com.zzh.youchat.ui.view.SettingsPage
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Settings

@Serializable
object Login

@Serializable
object Register


@Composable
fun YouChatApp(modifier: Modifier = Modifier) {
    val userViewModel: UserViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val navController = rememberNavController()
    val isLoggedIn = userViewModel.loginStatus.collectAsState()
    val context = LocalContext.current

    val TAG = "LOGIN_DEBUG"
    Log.d(TAG, "YouChatApplication: $isLoggedIn")

    NavHost(navController, startDestination = if (isLoggedIn.value) Main else Login) {
        composable<Main> {
            MainScreen(
                onNavigateToSettings = { navController.navigate(route = Settings) },
                onNavigateToLogin = { navController.navigate(route = Login) },
                saveLoginStatus = userViewModel::saveLoginStatus,
                modifier = modifier
            )
        }
        composable<Settings> { SettingsPage(modifier) }
        composable<Login> {
            LoginScreen(
                onNavigateToMain = {
                    userViewModel.saveLoginStatus(true)
                    navController.navigate(Main) {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Register) {
                        popUpTo(Register) {
                            inclusive = true
                        }
                    }
                },
                modifier = modifier,
            )
        }
        composable<Register> {
            RegisterScreen()
        }
    }

}

