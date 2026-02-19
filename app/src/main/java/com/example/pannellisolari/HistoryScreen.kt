package com.example.pannellisolari



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Helios", style = MaterialTheme.typography.headlineMedium)

        // Placeholder per l'immagine centrale dei flussi
        Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Visualizzazione Flussi Energetici", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Griglia dei dati
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoCard("Produzione", "5.2 kW", Modifier.weight(1f))
                InfoCard("Consumo", "1.8 kW", Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoCard("Rete (Vendita)", "+3.4 kW", Modifier.weight(1f))
                InfoCard("Batteria", "85%", Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Banner Allarme
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD32F2F)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "⚠️ ALLARME SURRISCALDAMENTO!\nEfficienza ridotta. Evita carichi pesanti.",
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}