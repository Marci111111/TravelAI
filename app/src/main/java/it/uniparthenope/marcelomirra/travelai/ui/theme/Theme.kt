package it.uniparthenope.marcelomirra.travelai.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Color(0xFF3366FF),
    primaryVariant = Color(0xFF254EDB),
    secondary = Color(0xFFFF4081)
)

@Composable
fun TravelAITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
