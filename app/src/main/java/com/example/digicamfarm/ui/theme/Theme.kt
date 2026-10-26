package com.example.digicamfarm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Green = Color(0xFF1F8236)
val GreenLight = Color(0xFF6FBF84)
val Surface = Color(0xFFF8F9FB)
val Warning = Color(0xFFE8B75B)

private val Colors = lightColorScheme(
    primary = Green,
    secondary = GreenLight,
    background = Surface,
    surface = Surface
)

@Composable
fun DigiCamFarmTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, content = content)
}