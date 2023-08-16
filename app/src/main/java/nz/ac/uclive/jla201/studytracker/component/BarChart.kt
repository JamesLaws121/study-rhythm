package nz.ac.uclive.jla201.studytracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
internal fun BarChart(
    modifier: Modifier = Modifier,
    values: List<Float>,
    colors: List<Color>,
    maxHeight: Int
) {
    val borderColor = Color.Black
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }
    val daysShorthand = listOf("mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val maxValue = values.max()

    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(maxHeight.dp)
                .drawBehind {
                    // draw X-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                    // draw Y-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Row() {
            values.forEachIndexed() {index, value ->
                val itemHeight = remember(value) { (value/maxValue) * maxHeight }

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .height(itemHeight.dp)
                        .width(10.dp)
                        .weight(1f)
                        .background(colors[index])
                        .align(Alignment.Bottom)
                )
            }
        }
        Row() {
            daysShorthand.forEach() {day ->
                Text(text = day)
            }
        }
    }
}
