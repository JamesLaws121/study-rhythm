package nz.ac.uclive.jla201.studytracker.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    inputTitles: List<String>,
    inputValues: List<Float>,
) {
    val chartDegrees = 360f
    var startAngle = 270f // start drawing clockwise (top to right)

    // calculate each input slice degrees from the input percentage
    val angleProgress = inputValues.map {
        (it * chartDegrees / inputValues.sum())
    }

    Column() {
        Row(){
            inputTitles.forEachIndexed { index, title ->
                Text(
                    text = title,
                    color = colors[index]
                )
            }
        }

        BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
            val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
            val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
            val canvasSizeDp = with(LocalDensity.current) { canvasSize.toDp() }

            Canvas(modifier = Modifier.size(canvasSizeDp)) {
                angleProgress.forEachIndexed { index, angle ->
                    val sliceWidth = 40.dp.toPx()
                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = angle,
                        useCenter = false,
                        size = size,
                        style = Stroke(width = sliceWidth)
                    )
                    startAngle += angle
                }
            }
        }
    }
}