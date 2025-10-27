package com.halimjr11.locaroo.ui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.halimjr11.locaroo.ui.organisms.HomeBottomBar
import com.halimjr11.locaroo.ui.molecules.BottomNavItem
import com.halimjr11.locaroo.ui.molecules.SearchFab

@Composable
fun HomeTemplate(
    bottomItems: List<BottomNavItem>,
    selectedIndex: Int,
    onBottomItemSelected: (Int) -> Unit,
    fabIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    onFabClick: () -> Unit = {},
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = {
            HomeBottomBar(
                items = bottomItems,
                selectedIndex = selectedIndex,
                onItemSelected = onBottomItemSelected
            )
        },
        floatingActionButton = {
            if (fabIcon != null) {
                SearchFab(icon = fabIcon, onClick = onFabClick)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            header()
            content()
        }
    }
}
