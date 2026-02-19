package com.example.pannellisolari

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

// Modello per i dati meteo giornalieri
data class WeatherForecast(
    val giorno: String,
    val tempMax: Int,
    val condizione: String,
    val produzioneStimataKwh: Float,
    val icona: String
)

@Composable
fun WeatherScreen() {
    // Dati di esempio basati sull'andamento del file Fotovoltaico.csv
    // e sulle medie di consumo/efficienza fornite
    val previsioni = listOf(
        WeatherForecast("Oggi", 32, "Soleggiato", 35.2f, "☀️"),
        WeatherForecast("Domani", 28, "Nuvoloso", 22.5f, "☁️"),
        WeatherForecast("Mercoledì", 25, "Pioggia", 12.8f, "🌧️"),
        WeatherForecast("Giovedì", 29, "Variabile", 28.4f, "⛅"),
        WeatherForecast("Venerdì", 31, "Soleggiato", 33.9f, "☀️")
    )

    // Modello per il grafico a barre della produzione stimata
    val chartModel = entryModelOf(*previsioni.map { it.produzioneStimataKwh }.toTypedArray())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "Prossimi 5 Giorni", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Grafico a barre delle previsioni di produzione
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Stima Produzione (kWh)", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Chart(
                        chart = columnChart(),
                        model = chartModel,
                        startAxis = rememberStartAxis(),
                        bottomAxis = rememberBottomAxis(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Lista dei dettagli meteo e consigli energetici
        items(previsioni) { forecast ->
            WeatherDetailRow(forecast)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SmartTipCard()
        }
    }
}

@Composable
fun WeatherDetailRow(forecast: WeatherForecast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = forecast.giorno, fontWeight = FontWeight.Bold)
                Text(text = "${forecast.condizione} • ${forecast.tempMax}°C", fontSize = 12.sp, color = Color.Gray)
            }
            Text(text = forecast.icona, fontSize = 28.sp)
            Text(text = "${forecast.produzioneStimataKwh} kWh", fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
        }
    }
}

@Composable
fun SmartTipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("💡 Consiglio Smart", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
            Text(
                "Oggi la produzione sarà massima tra le 12:00 e le 15:00. " +
                        "Pianifica i carichi pesanti (lavatrice, forno) in questa fascia oraria.",
                fontSize = 13.sp
            )
        }
    }
}