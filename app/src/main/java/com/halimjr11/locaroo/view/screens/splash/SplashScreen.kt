package com.halimjr11.locaroo.view.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.navigation.NavRoute
import com.halimjr11.locaroo.view.viewmodels.splash.SplashViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = false)

    // Finite, smooth entrance: scale-in with spring and fade-in with tween
    val scaleAnim = androidx.compose.runtime.remember { Animatable(0.85f) }
    val alphaAnim = androidx.compose.runtime.remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Run both animations in parallel and wait for completion
        coroutineScope {
            val scaleJob = launch {
                scaleAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            val alphaJob = launch {
                alphaAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
                )
            }
            // Wait for both to finish
            scaleJob.join(); alphaJob.join()
        }

        // Navigate after animation completes
        val target = if (isLoggedIn) NavRoute.Home.route else NavRoute.Login.route
        navController.navigate(target) {
            popUpTo(NavRoute.Splash.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.locaroo_trp),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
        )
    }
}

