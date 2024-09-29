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
import com.zzh.youchat.data.viewModel.LoginViewModel
import com.zzh.youchat.data.viewModel.SettingsViewModel
import com.zzh.youchat.ui.view.LoginScreen
import com.zzh.youchat.ui.view.MainScreen
import com.zzh.youchat.ui.view.SettingsPage
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Serializable
object Main

@Serializable
object Settings

@Serializable
object Login


@Composable
fun YouChatApp(modifier: Modifier = Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val navController = rememberNavController()
    val isLoggedIn = loginViewModel.loginStatus.collectAsState()
    val context = LocalContext.current

    val TAG = "LOGIN_DEBUG"
    Log.d(TAG, "YouChatApplication: $isLoggedIn")

    NavHost(navController, startDestination = if (isLoggedIn.value) Main else Login) {
        composable<Main> {
            MainScreen(
                onNavigateToSettings = { navController.navigate(route = Settings) },
                onNavigateToLogin = { navController.navigate(route = Login) },
                saveLoginStatus = loginViewModel::saveLoginStatus,
                modifier = modifier
            )
        }
        composable<Settings> { SettingsPage(modifier) }
        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    loginViewModel.saveLoginStatus(true)
                    navController.navigate(Main) {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                },
                modifier = modifier,
            )
        }
    }

}

