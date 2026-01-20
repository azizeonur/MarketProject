package com.example.getir.presention.map

import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getir.domain.address.Address
import com.example.getir.ui.theme.EmeraldGreen
import com.example.getir.ui.theme.TopAppBarMenu
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        Configuration.getInstance().load(
            context,
            PreferenceManager.getDefaultSharedPreferences(context)
        )
        onDispose { }
    }

    Scaffold(
        topBar = { TopAppBarMenu(onLogout = onLogout) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->

                    val mapView = MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(16.0)
                    }

                    val startPoint = GeoPoint(41.0394, 28.8569)
                    mapView.controller.setCenter(startPoint)

                    val marker = Marker(mapView).apply {
                        position = startPoint
                        setAnchor(
                            Marker.ANCHOR_CENTER,
                            Marker.ANCHOR_BOTTOM
                        )
                    }

                    mapView.overlays.add(marker)

                    val eventsOverlay = MapEventsOverlay(
                        object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                                marker.position = p
                                mapView.invalidate()

                                val fullAddress = runCatching {
                                    val geocoder = android.location.Geocoder(ctx)
                                    geocoder.getFromLocation(
                                        p.latitude,
                                        p.longitude,
                                        1
                                    )?.firstOrNull()
                                        ?.getAddressLine(0)
                                        .orEmpty()
                                }.getOrDefault("")

                                viewModel.onEvent(
                                    AddressEvent.SelectAddress(
                                        Address(
                                            id = "",
                                            userId = "",
                                            title = "Seçilen Adres",
                                            fullAddress = fullAddress,
                                            lat = p.latitude,
                                            lng = p.longitude,
                                            isDefault = false
                                        )
                                    )
                                )

                                viewModel.setLocation(
                                    lat = p.latitude,
                                    lng = p.longitude
                                )

                                return true
                            }


                            override fun longPressHelper(p: GeoPoint) = false
                        }
                    )
                    mapView.overlays.add(eventsOverlay)
                    mapView
                }
            )

            Button(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
            ) {
                Text("Bu adresi seç")
            }
        }
    }
}