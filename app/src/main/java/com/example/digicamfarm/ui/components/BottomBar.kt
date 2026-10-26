package com.example.digicamfarm.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.digicamfarm.ui.navigation.Routes

data class NavItem(val route: String, val label: String, val icon: ImageVector)

val bottomItems = listOf(
    NavItem(Routes.HOME, "Home", Icons.Filled.Home),
    NavItem(Routes.MARKETS, "Markets", Icons.AutoMirrored.Filled.TrendingUp),
    NavItem(Routes.WEATHER, "Weather", Icons.Filled.WbSunny),
    NavItem(Routes.FARM, "Farm", Icons.Filled.Eco),
    NavItem(Routes.LOANS, "Loans", Icons.Filled.AccountBalanceWallet),
)

@Composable
fun BottomBar(nav: NavController, current: String?) {
    NavigationBar(containerColor = Color.White) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = current == item.route,
                onClick = {
                    nav.navigate(item.route) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}