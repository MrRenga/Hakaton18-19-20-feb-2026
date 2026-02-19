package com.example.pannellisolari

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Caricamento dati dai file CSV forniti
    val productionAndConsumptionModel = remember { loadComparisonData(context) }
    val efficiencyModel = remember { loadEfficiencyData() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Storico", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Grafico 1: Produzione vs Consumo (Area Chart)
        Text("Produzione e Consumo Mensile", fontWeight = FontWeight.Bold)
        Card(modifier = Modifier.fillMaxWidth().height(250.dp).padding(vertical = 8.dp)) {
            Chart(
                chart = lineChart(),
                model = productionAndConsumptionModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Grafico 2: Salute Impianto (Degrado)
        Text("Salute Impianto e Batteria", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Efficienza Pannelli (Degrado)", fontWeight = FontWeight.Bold)
                Chart(
                    chart = lineChart(),
                    model = efficiencyModel,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                    modifier = Modifier.height(150.dp)
                )
                Text("Efficienza attuale: 94%. Perdita media annua: 0.8%.", fontSize = 12.sp)
            }
        }
    }
}

// Funzione per caricare i dati di confronto (File 1 e File 2)
fun loadComparisonData(context: Context): com.patrykandpatrick.vico.core.entry.ChartEntryModel {
    val productionEntries = mutableListOf<com.patrykandpatrick.vico.core.entry.ChartEntry>()
    val consumptionEntries = mutableListOf<com.patrykandpatrick.vico.core.entry.ChartEntry>()

    try {
        // Lettura Consumo (File: Consumo ...bbbb.csv) - Dati mensili 2012
        val consumptionStream = context.assets.open("Consumo ...bbbb.csv")
        val cReader = BufferedReader(InputStreamReader(consumptionStream))
        val cLines = cReader.readLines()
        // Riga 2012: indici 1-12 sono i mesi
        val data2012 = cLines.find { it.startsWith("2012") }?.split(",")
        data2012?.subList(1, 13)?.forEachIndexed { index, value ->
            consumptionEntries.add(entryOf(index.toFloat(), value.toFloat()))
        }

        // Lettura Produzione (File: Fotovoltaico.csv)
        // Nota: Nel file reale aggregheremmo per mese, qui simuliamo l'andamento stagionale
        val pPoints = listOf(350f, 380f, 450f, 520f, 600f, 650f, 680f, 640f, 550f, 420f, 360f, 320f)
        pPoints.forEachIndexed { index, value ->
            productionEntries.add(entryOf(index.toFloat(), value))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return com.patrykandpatrick.vico.core.entry.entryModelOf(productionEntries, consumptionEntries)
}

// Funzione per il grafico del degrado
fun loadEfficiencyData(): com.patrykandpatrick.vico.core.entry.ChartEntryModel {
    // Simulazione della perdita media annua dello 0.8%
    val efficiency = listOf(100f, 99.2f, 98.4f, 97.6f, 96.8f, 96.0f, 95.2f, 94.4f)
    return com.patrykandpatrick.vico.core.entry.entryModelOf(efficiency.mapIndexed { i, v -> entryOf(i, v) })
}