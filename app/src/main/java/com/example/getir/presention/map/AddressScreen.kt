package com.example.getir.presention.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.getir.domain.address.Address
import com.example.getir.ui.theme.EmeraldGreen
import com.example.getir.ui.theme.TopAppBarMenu
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun AddressScreen(
    onLogout: () -> Unit,
    onOpenMap: () -> Unit,
    onBack:() -> Unit,
    onNavigateToPayment: () -> Unit,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var addressText by remember { mutableStateOf("") }

    LaunchedEffect(uiState.selectedAddress) {

        uiState.selectedAddress?.let { selected ->
            addressText = selected.fullAddress
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(AddressEvent.LoadAddresses)
    }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateToPayment()
            viewModel.onEvent(AddressEvent.ClearMessage)
        }
    }

    Scaffold(
        topBar = { TopAppBarMenu(onLogout = onLogout) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                "Lütfen teslimat adresinizi giriniz:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = addressText,
                onValueChange = { addressText = it },
                placeholder = { Text("Adresinizi yazınız...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = onOpenMap,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Haritadan bul", color = EmeraldGreen)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onEvent(
                        AddressEvent.SaveAddress(
                            Address(
                                id = "",
                                userId = "",
                                title = "Yeni Adres",
                                fullAddress = addressText,
                                lat = uiState.selectedLat ?: 0.0,
                                lng = uiState.selectedLng ?: 0.0,
                                isDefault = true
                            )
                        )
                    )
                },
                enabled = addressText.isNotBlank() && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Devam Et")
                }
            }

            uiState.error?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}