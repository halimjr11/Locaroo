package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.molecules.TopBarProfile

@Composable
fun HomeHeader(
    userName: String,
    onNotifClick: () -> Unit = {}
) {
    Column(Modifier.padding(all = 16.dp)) {
        TopBarProfile(name = userName, onNotifClick = onNotifClick)
        Spacer(
            Modifier.height(12.dp)
        )
        Text(
            text = stringResource(R.string.tagline_1),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        val title = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(stringResource(R.string.tagline_2))
            }
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(stringResource(R.string.tagline_3))
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(16.dp))
    }
}
