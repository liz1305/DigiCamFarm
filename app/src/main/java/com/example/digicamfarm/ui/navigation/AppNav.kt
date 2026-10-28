package com.example.digicamfarm.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.digicamfarm.ui.components.BottomBar
import com.example.digicamfarm.ui.components.bottomItems
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import com.example.digicamfarm.ui.screens.WelcomeScreen

@Composable
fun Placeholder(name: String, onNext: (() -> Unit)? = null) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(name, style = MaterialTheme.typography.headlineMedium)
        if (onNext != null) Button(onClick = onNext) { Text("Next") }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val current = nav.currentBackStackEntryAsState().value?.destination?.route
    val showBar = bottomItems.any { it.route == current }

    Scaffold(
        bottomBar = { if (showBar) BottomBar(nav, current) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.WELCOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.WELCOME) {
                WelcomeScreen(
                    onGetStarted = { nav.navigate(Routes.REGISTER) },
                    onLogin = { nav.navigate(Routes.LOGIN) }
                )
            }
            composable(Routes.LOGIN) { Placeholder("Login") { nav.navigate(Routes.HOME) } }
            composable(Routes.REGISTER) { Placeholder("Register") }
            composable(Routes.HOME) { Placeholder("Home") }
            composable(Routes.MARKETS) { Placeholder("Markets") }
            composable(Routes.WEATHER) { Placeholder("Weather") }
            composable(Routes.FARM) { Placeholder("Cost & Profit") }
            composable(Routes.LOANS) { Placeholder("Loans") }
            // add the rest the same way as you build them
        }
    }
}