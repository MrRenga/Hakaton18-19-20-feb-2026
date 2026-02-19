package com.example.pannellisolari

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvReader {
    fun readEnergyData(context: Context): EnergyData {
        var totalConsumo = 0.0
        var totalProduzione = 0.0

        try {
            // Leggi Consumo.csv
            context.assets.open("Consumo.csv").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    // Salta l'intestazione se presente
                    val header = reader.readLine()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        val tokens = line.split(",")
                        if (tokens.isNotEmpty()) {
                            // Assumiamo che il valore del consumo sia in una colonna specifica, es. la seconda (indice 1)
                            // Adatta l'indice in base alla struttura del tuo CSV
                            val value = tokens.lastOrNull()?.toDoubleOrNull() ?: 0.0
                            totalConsumo += value
                        }
                        line = reader.readLine()
                    }
                }
            }

            // Leggi Fotovoltaico.csv
            context.assets.open("Fotovoltaico.csv").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    val header = reader.readLine()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        val tokens = line.split(",")
                        if (tokens.isNotEmpty()) {
                            // Assumiamo che il valore della produzione sia in una colonna specifica
                            val value = tokens.lastOrNull()?.toDoubleOrNull() ?: 0.0
                            totalProduzione += value
                        }
                        line = reader.readLine()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Calcoli d'esempio per popolare EnergyData
        // Nota: Qui sto sommando tutto, ma potresti voler prendere solo l'ultimo valore o fare una media
        // Per ora prendiamo un valore "istantaneo" fittizio basato sui totali o l'ultimo record per demo
        val produzione = Math.round(totalProduzione * 10.0) / 10.0
        val consumo = Math.round(totalConsumo * 10.0) / 10.0
        val rete = Math.round((produzione - consumo) * 10.0) / 10.0
        
        return EnergyData(
            produzione = produzione,
            consumo = consumo,
            rete = rete,
            batteriaPercentuale = 75, // Valore di default o calcolato
            batteriaAutonomia = "5h 20m"
        )
    }
}
