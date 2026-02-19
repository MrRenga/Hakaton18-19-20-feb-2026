package com.example.pannellisolari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pannellisolari.ui.theme.PannelliSolariTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Dati di esempio (in un caso reale verrebbero caricati dal CSV)
        val exampleData = EnergyData(
            produzione = 5.2,
            consumo = 1.8,
            rete = 3.4,
            batteriaPercentuale = 85,
            batteriaAutonomia = "6h 30m"
        )

        setContent {
            PannelliSolariTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(data = exampleData)
                }
            }
        }
    }
}
