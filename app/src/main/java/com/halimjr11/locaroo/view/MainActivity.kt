package com.halimjr11.locaroo.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.halimjr11.locaroo.common.AuthEvent
import com.halimjr11.locaroo.ui.navigation.AppNavGraph
import com.halimjr11.locaroo.ui.navigation.NavRoute
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.view.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocarooTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    LaunchedEffect(Unit) {
                        viewModel.authEvents.collect { event ->
                            println("Jalanan ==> data $event")
                            if (event is AuthEvent.Unauthorized) {
                                navController.navigate(NavRoute.Login.route)
                            }
                        }
                    }
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
