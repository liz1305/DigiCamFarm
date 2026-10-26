package com.example.digicamfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.digicamfarm.ui.navigation.AppNav
import com.example.digicamfarm.ui.theme.DigiCamFarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigiCamFarmTheme {
                AppNav()
            }
        }
    }
}