package com.example.pannellisolari


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherScreen() {
    val consigli = listOf(
        "Oggi: Consigliati lavaggi base tra le 12:00 e le 15:00.",
        "Domani: Possibili nubi, carica la batteria al mattino.",
        "Venerdì: Picco di produzione previsto, usa elettrodomestici pesanti."
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Prossimi 5 Giorni", style = MaterialTheme.typography.titleLarge)

        // Placeholder grafico meteo
        Card(modifier = Modifier.fillMaxWidth().height(150.dp).padding(vertical = 16.dp)) {
            Box(Modifier.fillMaxSize()) { Text("Grafico Previsioni Solari", Modifier.padding(16.dp)) }
        }

        Text("Smart Weather Tips", style = MaterialTheme.typography.titleMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(consigli) { consiglio ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(consiglio, modifier = Modifier.padding(12.dp), fontSize = 14.sp)
                }
            }
        }
    }
}