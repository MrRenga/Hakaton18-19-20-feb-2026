package com.example.pannellisolari

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// IMPORT FONDAMENTALE MANCANTE:
import androidx.compose.foundation.layout.Arrangement
// Import Vico
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.compose.chart.line.lineSpec

@Composable
fun AdvisorScreen() {
    // Dati di esempio
    val consumiMensili = listOf(430f, 380f, 390f, 370f, 380f, 400f, 480f, 500f, 420f, 380f, 360f, 410f)
    val produzioneStimata = listOf(300f, 350f, 500f, 600f, 750f, 850f, 900f, 880f, 700f, 500f, 320f, 280f)

    // Creazione del modello dati (avvolto in remember per evitare ricalcoli)
    val chartModel = remember {
        entryModelOf(
            consumiMensili.mapIndexed { index, f -> entryOf(index, f) },
            produzioneStimata.mapIndexed { index, f -> entryOf(index, f) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Smart Advisor", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = "Ottimizzazione e Vendita", color = Color.Gray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Card "VENDI ORA"
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("VENDI ORA!", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text("(Surplus attuale rilevato)", color = Color.White.copy(alpha = 0.8f))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Confronto Produzione vs Consumo",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )

        // Grafico
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F3F4))
        ) {
            Chart(
                chart = lineChart(
                    // Definiamo i colori delle linee per farle corrispondere alla legenda
                    lines = listOf(
                        lineSpec(lineColor = Color(0xFFD32F2F)), // Rosso Consumo
                        lineSpec(lineColor = Color(0xFF4CAF50))  // Verde Produzione
                    )
                ),
                model = chartModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }

        // Legenda
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Ora funzionerà correttamente
        ) {
            LegendItem("Consumo", Color(0xFFD32F2F))
            LegendItem("Produzione", Color(0xFF4CAF50))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Analisi: Il picco estivo di consumo (500kWh) è ben bilanciato dalla produzione. Valuta la vendita del surplus a Giugno.",
            fontSize = 13.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

fun newFunction() {
    implementation("com.patrykandpatrick.vico:compose:1.13.1")
    implementation("com.patrykandpatrick.vico:compose-m3:1.13.1")
    implementation("com.patrykandpatrick.vico:core:1.13.1")
}