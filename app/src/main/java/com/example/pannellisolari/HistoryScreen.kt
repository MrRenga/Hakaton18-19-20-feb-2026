package com.example.pannellisolari

import android.content.Context
import android.util.Log
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
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val productionAndConsumptionModel = remember { loadComparisonData(context) }
    val efficiencyModel = remember { loadEfficiencyData() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Storico Energetico", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Confronto Produzione e Consumo (2012)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = 12.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
        ) {
            Chart(
                chart = lineChart(),
                model = productionAndConsumptionModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Analisi Efficienza a Lungo Termine", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Degrado Pannelli (Efficienza %)", fontWeight = FontWeight.Medium)
                Box(modifier = Modifier.height(200.dp)) {
                    Chart(
                        chart = lineChart(),
                        model = efficiencyModel,
                        startAxis = rememberStartAxis(),
                        bottomAxis = rememberBottomAxis(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Efficienza attuale: 94.4%. Perdita media stimata: 0.8% annuo.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

fun loadComparisonData(context: Context): ChartEntryModel {
    // Usiamo esplicitamente List<ChartEntry> per evitare ambiguità con Kotlin 2.x
    val consumptionEntries = mutableListOf<ChartEntry>()
    val productionEntries = mutableListOf<ChartEntry>()

    val productionData = listOf(350f, 380f, 450f, 520f, 600f, 650f, 680f, 640f, 550f, 420f, 360f, 320f)

    try {
        val inputStream = context.assets.open("Consumo.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val lines = reader.readLines()

        val data2012 = lines.find { it.trim().startsWith("2012") }?.split(",")

        if (data2012 != null && data2012.size >= 13) {
            data2012.subList(1, 13).forEachIndexed { index, value ->
                val cleanValue = value.trim().replace("\"", "").toFloatOrNull() ?: 0f
                consumptionEntries.add(entryOf(index, cleanValue))
            }
        }
    } catch (e: Exception) {
        Log.e("CSV_LOAD", "Errore: ${e.message}")
    }

    // Fallback se il CSV fallisce o è vuoto
    if (consumptionEntries.isEmpty()) {
        val fallbackCons = listOf(400f, 390f, 370f, 350f, 340f, 360f, 380f, 400f, 420f, 440f, 450f, 460f)
        fallbackCons.forEachIndexed { i, v -> consumptionEntries.add(entryOf(i, v)) }
    }

    // Popoliamo la produzione
    productionData.forEachIndexed { index, value ->
        productionEntries.add(entryOf(index, value))
    }

    // SOLUZIONE ERRORE:
    // Avvolgiamo le liste in listOf() per chiamare l'overload List<List<ChartEntry>>
    // Questo è il modo più stabile per Kotlin 2.x
    return entryModelOf(listOf(consumptionEntries as List<ChartEntry>, productionEntries as List<ChartEntry>))
}

fun loadEfficiencyData(): ChartEntryModel {
    val efficiency = listOf(100f, 99.2f, 98.4f, 97.6f, 96.8f, 96.0f, 95.2f, 94.4f)
    val entries = efficiency.mapIndexed { i, v -> entryOf(i, v) }

    // SOLUZIONE ERRORE:
    // Casting esplicito a List<ChartEntry> per la serie singola
    return entryModelOf(entries as List<ChartEntry>)
}