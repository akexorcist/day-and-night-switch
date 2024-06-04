import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akexorcist.kotlin.multiplatform.dayandnight.DayAndNightContainer
import com.akexorcist.kotlin.multiplatform.dayandnight.DayAndNightSwitch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var selected by remember { mutableStateOf(true) }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DayAndNightContainer(
                modifier = Modifier.fillMaxSize(),
                selected = selected,
            ) {
                DayAndNightSwitch(
                    modifier = Modifier.scale(2f),
                    selected = selected,
                    onSwitchToggle = { selected = !selected },
                )
            }
            PoweredByLabel(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 24.dp),
                selected = selected,
            )
        }
    }
}

@Composable
private fun PoweredByLabel(
    modifier: Modifier = Modifier,
    selected: Boolean,
) {
    val color by animateColorAsState(
        targetValue = when (selected) {
            true -> Color.Black
            false -> Color.White
        },
        animationSpec = tween(
            durationMillis = 700,
        ),
        label = "text_color",
    )
    Row(modifier = modifier) {
        Text(
            text = "Powered by",
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            color = color,
        )
        Spacer(Modifier.width(3.dp))
        Text(
            text = "Compose Multiplatform",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = color,
        )
    }
}
