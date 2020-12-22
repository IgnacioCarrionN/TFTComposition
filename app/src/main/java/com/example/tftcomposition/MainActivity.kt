package com.example.tftcomposition

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.builder.ui.BuilderView
import com.example.builder.viewmodel.BuilderViewModel
import com.example.home.ui.HomeView
import com.example.home.viewmodel.HomeViewModel
import com.example.tftcomposition.ui.TFTCompositionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val builderViewModel: BuilderViewModel by viewModels()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TFTCompositionTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeView(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(
                        route = "builder/?compId={compId}",
                        arguments = listOf(navArgument("compId") {
                            nullable = true
                            defaultValue = null
                        })
                    ) { backStackEntry ->
                        BuilderView(
                            builderViewModel,
                            backStackEntry.arguments?.getString("compId")?.toLong()
                        )
                    }
                }
            }

        }
    }
}