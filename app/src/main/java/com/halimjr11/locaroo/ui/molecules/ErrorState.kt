package com.halimjr11.locaroo.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.atoms.BodyM
import com.halimjr11.locaroo.ui.atoms.TitleXL

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    title: String = "Oops!",
    message: String,
    onRetry: (() -> Unit)? = null,
    retryLabel: String = "Retry"
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Lottie animation
        val compositionResult = rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.empty)
        )
        val composition = compositionResult.value
        val progress = animateLottieCompositionAsState(composition).progress

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(200.dp)
        )

        TitleXL(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        BodyM(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (onRetry != null) {
            PrimaryButton(
                text = retryLabel,
                onClick = onRetry
            )
        }
    }
}
