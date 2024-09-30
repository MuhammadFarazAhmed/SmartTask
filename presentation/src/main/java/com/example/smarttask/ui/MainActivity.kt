package com.example.smarttask.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.smarttask.MainGraph
import com.example.smarttask.R
import com.example.smarttask.ui.theme.SmartTaskTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var isSplashScreenVisible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreenForSingleActivity()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartTaskTheme {
                SetStatusBarColor()
                App()
            }
        }
    }

    private fun setupSplashScreenForSingleActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    isSplashScreenVisible
                }
            }
            lifecycleScope.launch {
                delay(2000)
                isSplashScreenVisible = false
            }
        } else {
            // For lower API levels, set the splash theme manually if needed
            setTheme(R.style.Theme_SmartTask)
        }
    }
}

@Composable
private fun App() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MainGraph(innerPadding)
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.intro),
            contentDescription = ""
        )
    }
}

@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val primaryColor = MaterialTheme.colorScheme.primary

    systemUiController.setStatusBarColor(
        color = primaryColor,
        darkIcons = false
    )
}