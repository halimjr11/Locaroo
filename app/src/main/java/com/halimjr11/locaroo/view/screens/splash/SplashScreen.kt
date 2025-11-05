package com.halimjr11.locaroo.view.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocarooTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val scaleAnim = androidx.compose.runtime.remember { Animatable(0f) }
                    val alphaAnim = androidx.compose.runtime.remember { Animatable(0f) }

                    LaunchedEffect(Unit) {
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
                                    animationSpec = tween(
                                        durationMillis = 1000,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                            scaleJob.join()
                            alphaJob.join()
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
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
            }
        }
    }
}
