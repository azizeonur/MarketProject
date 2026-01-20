package com.example.getir.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMenu(
    onLogout: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("My Lovely Shop", color = Color.White) },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Menu", tint = Color.White)
            }

            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        menuExpanded = false
                        onLogout()
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldGreen)
    )
}