package nz.ac.uclive.jla201.studytracker.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

    // calculate each input slice degrees from the input
    val angleProgress = inputValues.map {
        (it * chartDegrees / inputValues.sum())
    }

    Row(modifier = Modifier
        .padding(10.dp)) {
        Column(modifier = Modifier
            .padding(10.dp)) {
            inputTitles.forEachIndexed { index, title ->
                Row(){
                    Text(
                        text = String.format("%s %.2fh",title, inputValues[index]),
                        color = colors[index]
                    )
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(10.dp)
                            .height(10.dp)
                            .background(colors[index])
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }

        BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
            val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
            val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
            val canvasSizeDp = with(LocalDensity.current) { canvasSize.toDp() }

            val animateFloat = remember { Animatable(0f) }
            LaunchedEffect(animateFloat) {
                animateFloat.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing))
            }

            Canvas(modifier = Modifier.size(canvasSizeDp)) {
                angleProgress.forEachIndexed { index, angle ->
                    val sliceWidth = 40.dp.toPx()
                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = angle * animateFloat.value,
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