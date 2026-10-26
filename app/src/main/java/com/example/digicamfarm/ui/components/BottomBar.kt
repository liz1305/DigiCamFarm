package com.example.digicamfarm.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.digicamfarm.R
import com.example.digicamfarm.ui.navigation.Routes

data class NavItem(val route: String, val label: String, @DrawableRes val icon: Int)

val bottomItems = listOf(
    NavItem(Routes.HOME, "Home", R.drawable.ic_home),
    NavItem(Routes.MARKETS, "Markets", R.drawable.ic_market),
    NavItem(Routes.WEATHER, "Weather", R.drawable.ic_weather),
    NavItem(Routes.FARM, "Farm", R.drawable.ic_farm),
    NavItem(Routes.LOANS, "Loans", R.drawable.ic_loan),
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
                icon = { Icon(painterResource(item.icon), contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1F8236),
                    selectedTextColor = Color(0xFF1F8236),
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}