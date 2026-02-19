package com.example.pannellisolari

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

data class EnergyData(
    val produzione: Double,
    val consumo: Double,
    val rete: Double,
    val batteriaPercentuale: Int,
    val batteriaAutonomia: String
)

@Composable
fun HomeScreen(data: EnergyData) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Helios",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Central Energy Flow Diagram
            EnergyFlowDiagram(data)

            Spacer(modifier = Modifier.height(32.dp))

            // Summary Cards Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    StatusCard(
                        title = "Produzione",
                        value = "${data.produzione} kW",
                        icon = Icons.Default.WbSunny,
                        iconColor = Color(0xFFFFB300)
                    )
                }
                item {
                    StatusCard(
                        title = "Consumo",
                        value = "${data.consumo} kW",
                        icon = Icons.Default.Home,
                        iconColor = Color(0xFFE57373)
                    )
                }
                item {
                    StatusCard(
                        title = "Rete (Vendita)",
                        value = "${if (data.rete >= 0) "+" else ""}${data.rete} kW",
                        icon = Icons.Default.Power,
                        iconColor = Color(0xFF7986CB)
                    )
                }
                item {
                    StatusCard(
                        title = "Batteria",
                        value = "${data.batteriaPercentuale}%",
                        icon = Icons.Default.BatteryFull,
                        iconColor = Color(0xFF81C784)
                    )
                }
            }
        }
    }
}

@Composable
fun EnergyFlowDiagram(data: EnergyData) {
    Box(
        modifier = Modifier
            .size(280.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Central Arrows (Simplified representation with Canvas)
        Canvas(modifier = Modifier.size(150.dp)) {
            // This is a simplified version of the curved arrows in the image
            val strokeWidth = 4.dp.toPx()
            
            // Production to Grid (Top to Right)
            drawPath(
                path = Path().apply {
                    moveTo(size.width / 2, 0f)
                    quadraticTo(size.width * 0.8f, size.height * 0.2f, size.width, size.height / 2)
                },
                color = Color(0xFF81C784),
                style = Stroke(width = strokeWidth)
            )
            
            // Production to Battery (Top to Bottom)
            drawPath(
                path = Path().apply {
                    moveTo(size.width / 2, 0f)
                    lineTo(size.width / 2, size.height)
                },
                color = Color(0xFFFFB300),
                style = Stroke(width = strokeWidth)
            )

            // Production to Home (Top to Left)
            drawPath(
                path = Path().apply {
                    moveTo(size.width / 2, 0f)
                    quadraticTo(size.width * 0.2f, size.height * 0.2f, 0f, size.height / 2)
                },
                color = Color(0xFFE57373),
                style = Stroke(width = strokeWidth)
            )
        }

        // Icons around the center
        // Top: Produzione
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(40.dp))
            Text("Produzione", fontSize = 12.sp)
            Text("${data.produzione} kW", fontWeight = FontWeight.Bold)
        }

        // Left: Consumo
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color(0xFFE57373), modifier = Modifier.size(40.dp))
            Text("Consumo", fontSize = 12.sp)
            Text("${data.consumo} kW", fontWeight = FontWeight.Bold)
        }

        // Right: Rete
        Column(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Power, contentDescription = null, tint = Color(0xFF7986CB), modifier = Modifier.size(40.dp))
            Text("Rete (Vendita)", fontSize = 12.sp)
            Text("${if (data.rete >= 0) "+" else ""}${data.rete} kW", fontWeight = FontWeight.Bold)
        }

        // Bottom: Batteria
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.BatteryFull, contentDescription = null, tint = Color(0xFF81C784), modifier = Modifier.size(40.dp))
            Text("Batteria", fontSize = 12.sp)
            Text("${data.batteriaPercentuale}%", fontWeight = FontWeight.Bold)
            Text("Autonomia: ${data.batteriaAutonomia}", fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 12.sp, color = Color.Gray)
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.WbSunny, contentDescription = "Stats") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Devices") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = "Charts") },
            selected = false,
            onClick = { }
        )
    }
}
