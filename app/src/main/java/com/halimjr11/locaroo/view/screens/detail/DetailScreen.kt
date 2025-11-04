package com.halimjr11.locaroo.view.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.ErrorState
import com.halimjr11.locaroo.ui.molecules.IconTextRow
import com.halimjr11.locaroo.ui.molecules.PrimaryButton
import com.halimjr11.locaroo.ui.organisms.AboutSection
import com.halimjr11.locaroo.ui.organisms.TopImageHeader
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.view.viewmodels.detail.DetailViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.detailState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is UiState.Error -> {
                ErrorState(
                    modifier = modifier.align(Alignment.Center),
                    title = stringResource(R.string.error_title),
                    message = (state as UiState.Error).message,
                    onRetry = {
                        viewModel.retry()
                    }
                )
            }

            is UiState.Loading -> {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                if (showDatePicker.value) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker.value = false },
                        confirmButton = {
                            TextButton(onClick = { showDatePicker.value = false }) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDatePicker.value = false
                            }) { Text("Cancel") }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                DetailScreenContent(
                    modifier,
                    (state as UiState.Success<PlaceUi>).data,
                    isFavorite = isFavorite,
                    onBack = onBack,
                    onFavorite = { viewModel.toggleFavorite() },
                    onPlanNow = { showDatePicker.value = true }
                )
            }
        }
    }
}


@Composable
private fun DetailScreenContent(
    modifier: Modifier = Modifier,
    place: PlaceUi,
    isFavorite: Boolean,
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onPlanNow: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        TopImageHeader(
            image = place.imageUrl ?: "",
            onBack = onBack,
            onFavorite = onFavorite,
            isFavorite = isFavorite,
            favoriteIcon = painterResource(R.drawable.ic_favorite),
            backIcon = painterResource(R.drawable.ic_back)
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title + host avatar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = place.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconTextRow(
                        icon = rememberVectorPainter(Icons.Filled.LocationOn),
                        text = place.location
                    )
                    Text(text = "•", color = MaterialTheme.colorScheme.onSurface)
                    IconTextRow(
                        icon = rememberVectorPainter(Icons.Filled.Star),
                        iconTint = MaterialTheme.colorScheme.primary,
                        text = place.rating.toString()
                    )
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(16.dp))
                AboutSection(
                    description = place.description
                )
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Plan Now", modifier = Modifier.fillMaxWidth()) { onPlanNow() }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreviewLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        DetailScreenContent(
            place = PlaceUi(
                id = 0,
                name = "Niladri Reservoir",
                location = "Tekergat, Sunamganj",
                description = "You will get a complete travel package on the beaches. Packages in the form of airline tickets, recommended hotel rooms, transportation. Have you ever been on holiday to the Greek, etc...",
                imageUrl = "",
                rating = 4.7,
                reviewsCount = 10,
                latitude = 0.0,
                longitude = 0.0
            ),
            isFavorite = false,
            onBack = {},
            onFavorite = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        DetailScreenContent(
            place = PlaceUi(
                id = 0,
                name = "Niladri Reservoir",
                location = "Tekergat, Sunamganj",
                description = "You will get a complete travel package on the beaches. Packages in the form of airline tickets, recommended hotel rooms, transportation. Have you ever been on holiday to the Greek, etc...",
                imageUrl = "",
                rating = 4.7,
                reviewsCount = 10,
                latitude = 0.0,
                longitude = 0.0
            ),
            isFavorite = false,
            onBack = {},
            onFavorite = {}
        )
    }
}
