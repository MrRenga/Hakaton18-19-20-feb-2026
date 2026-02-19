package com.example.pannellisolari



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdvisorScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Smart Advisor", style = MaterialTheme.typography.headlineSmall)
        Text("Ottimizzazione e Vendita", color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Bottone "VENDI ORA" con gradiente (simulato con colore fisso per ora)
        Surface(
            modifier = Modifier.fillMaxWidth().height(140.dp),
            color = Color(0xFF4CAF50),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("VENDI ORA!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Text("(Immetti in rete)", color = Color.White, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Finestre di Mercato", style = MaterialTheme.typography.titleMedium)

        // Simulazione Timeline Mercato
        LinearProgressIndicator(
            progress = { 0.7f },
            modifier = Modifier.fillMaxWidth().height(20.dp).padding(vertical = 8.dp),
            color = Color.Yellow,
            trackColor = Color.LightGray
        )
        Text("Picco vendita tra le 14:00 e le 16:00", fontSize = 12.sp, color = Color.Gray)
    }
}