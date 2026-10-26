package com.example.digicamfarm.ui.navigation

import androidx.compose.runtime.Composable

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

    Scaffold(bottomBar = { if (showBar) BottomBar(nav, current) }) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.WELCOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.WELCOME) { Placeholder("Welcome") { nav.navigate(Routes.LOGIN) } }
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