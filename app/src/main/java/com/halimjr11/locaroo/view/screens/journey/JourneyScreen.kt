package com.halimjr11.locaroo.view.screens.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.model.ScheduleItemUi
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun JourneyScreen(modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    val startOfWeek =
        remember(today) { today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)) }
    val daysOfWeek = remember(startOfWeek) { (0..6).map { startOfWeek.plusDays(it.toLong()) } }

    var selectedDate by remember { mutableStateOf(today) }

    val all = remember(daysOfWeek) {
        buildList {
            daysOfWeek.forEachIndexed { i, d ->
                add(
                    ScheduleItemUi(
                        i + 1L,
                        d.format(DateTimeFormatter.ofPattern("d MMMM")),
                        "Niladi Reservoir",
                        "Tekangir, Sunonyi"
                    )
                )
                if (i % 2 == 0) add(
                    ScheduleItemUi(
                        100 + i + 1L,
                        d.format(DateTimeFormatter.ofPattern("d MMMM")),
                        "High Rech Park",
                        "Zero Point, Sylhet"
                    )
                )
            }
        }
    }
    val itemsForDay = remember(
        selectedDate,
        all
    ) { all.filter { it.date == selectedDate.format(DateTimeFormatter.ofPattern("d MMMM")) } }

    JourneyScreenContent(
        items = itemsForDay,
        selectedDate = selectedDate,
        daysOfWeek = daysOfWeek,
        onPrev = { selectedDate = selectedDate.minusDays(1) },
        onNext = { selectedDate = selectedDate.plusDays(1) },
        onSelect = { selectedDate = it }
    )
}

@Composable
private fun JourneyScreenContent(
    modifier: Modifier = Modifier,
    items: List<ScheduleItemUi>,
    selectedDate: LocalDate,
    daysOfWeek: List<LocalDate>,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSelect: (LocalDate) -> Unit,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            WeekHeader(
                date = selectedDate,
                daysOfWeek = daysOfWeek,
                onPrev = onPrev,
                onNext = onNext,
                onSelect = onSelect
            )
            SectionHeader()
            ScheduleList(items)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = { Text("My Journey Plan", style = MaterialTheme.typography.titleLarge) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun WeekHeader(
    date: LocalDate,
    daysOfWeek: List<LocalDate>,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSelect: (LocalDate) -> Unit,
) {
    val titleFormatter = remember { DateTimeFormatter.ofPattern("d MMMM") }
    val dowFormatter = remember { DateTimeFormatter.ofPattern("E") }
    val dayFormatter = remember { DateTimeFormatter.ofPattern("d") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onPrev) {
                    Icon(
                        Icons.Filled.ChevronLeft,
                        contentDescription = "Previous"
                    )
                }
                Text(
                    text = date.format(titleFormatter),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = onNext) {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "Next"
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daysOfWeek.forEach { d ->
                    val isSelected = d == date
                    val bg = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                    val fg = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Column(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .background(bg)
                            .clickable { onSelect(d) }
                            .padding(vertical = 8.dp, horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            d.format(dowFormatter).take(1),
                            style = MaterialTheme.typography.labelSmall,
                            color = fg
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            d.format(dayFormatter),
                            style = MaterialTheme.typography.titleSmall,
                            color = fg
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "My Schedule",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            "View all",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ScheduleList(items: List<ScheduleItemUi>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items) { item ->
            ScheduleCard(item)
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun ScheduleCard(item: ScheduleItemUi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Thumbnail placeholder
            Column(
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    item.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJourneyLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        JourneyScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJourneyDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        JourneyScreen()
    }
}
