package com.benedetto.awsfetchjobs.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benedetto.awsfetchjobs.ui.viewmodels.ItemViewModel
import com.benedetto.awsfetchjobs.ui.views.HomeScreen

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()
    val itemViewModel: ItemViewModel = hiltViewModel()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(modifier, itemViewModel)
            }
        }
    }
}
