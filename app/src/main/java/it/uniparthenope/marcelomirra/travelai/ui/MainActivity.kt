package it.uniparthenope.marcelomirra.travelai.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import it.uniparthenope.marcelomirra.travelai.ui.theme.TravelAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelAITheme {
                TravelAIApp()
            }
        }
    }
}
