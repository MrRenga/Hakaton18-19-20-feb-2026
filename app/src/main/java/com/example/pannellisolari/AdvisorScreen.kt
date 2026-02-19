package com.example.pannellisolari

import android.content.Context // Aggiunto
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Aggiunto per il contesto
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import java.io.BufferedReader // Aggiunto per il file
import java.io.InputStreamReader // Aggiunto per il file

@Composable
fun AdvisorScreen() {
    val context = LocalContext.current // Prendi il contesto per accedere agli assets

    // PRODUZIONE: Resta simulata (o carica anche questa se hai un altro file)
    val produzioneStimata = listOf(300f, 350f, 500f, 600f, 750f, 850f, 900f, 880f, 700f, 500f, 320f, 280f)

    // CONSUMO: Caricato dall'asset "Consumo.csv"
    val consumiMensili = remember { loadConsumoFromAssets(context) }

    val chartModel = remember {
        entryModelOf(
            consumiMensili.mapIndexed { index, f -> entryOf(index, f) },
            produzioneStimata.mapIndexed { index, f -> entryOf(index, f) }
        )
    }

    // ... (Tutto il resto della UI resta uguale a come l'hai scritta nel file originale)

    // Sostituisci la parte superiore e incolla la funzione in fondo
}

// NUOVA FUNZIONE PER LEGGERE L'ASSET "Consumo.csv"
fun loadConsumoFromAssets(context: Context): List<Float> {
    val fallbackData = listOf(430f, 380f, 390f, 370f, 380f, 400f, 480f, 500f, 420f, 380f, 360f, 410f)
    try {
        val inputStream = context.assets.open("Consumo.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val lines = reader.readLines()

        // Supponendo che il file CSV abbia i consumi per l'anno 2012
        // Cerchiamo la riga che inizia con "2012"
        val data2012 = lines.find { it.trim().startsWith("2012") }?.split(",")

        return if (data2012 != null && data2012.size >= 13) {
            // Estrae i 12 mesi ignorando il primo campo (l'anno)
            data2012.subList(1, 13).map { it.trim().toFloatOrNull() ?: 0f }
        } else {
            fallbackData // Se il file è vuoto o la riga 2012 non esiste
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return fallbackData // Se il file non esiste proprio
    }
}