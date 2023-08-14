package nz.ac.uclive.jla201.studytracker.component

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import android.graphics.Color
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel

@Composable
fun StatisticsScreen() {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(StudyTrackerApplication.subjectRepository)
    val sessionViewModel = SessionViewModel(StudyTrackerApplication.sessionRepository)

    val colors = listOf(
        Color(0xff6050dc),
        Color(0xffc435c0),
        Color(0xfffc2296),
        Color(0xffff4567),
        Color(0xffff773a),
        Color(0xffffa600)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Statistics",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        PieChartScreen(colors)
    }
}

@Composable
internal fun PieChartScreen(colors : List<Color>) {
    val inputValues = listOf(0.2F, 0.3F, 0.5F)
    val inputTitles = listOf("Math", "English", "Science")

    val chartColors = colors.slice(IntRange(0, inputTitles.size))

    DonutChart(
        modifier = Modifier.padding(20.dp),
        colors = chartColors,
        inputValues = inputValues,
        inputTitles = inputTitles,
    )

}