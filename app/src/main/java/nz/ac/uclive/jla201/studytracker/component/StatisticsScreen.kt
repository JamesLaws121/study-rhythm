package nz.ac.uclive.jla201.studytracker.component

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel

@Composable
fun StatisticsScreen() {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(StudyTrackerApplication.subjectRepository)
    val sessionViewModel = SessionViewModel(StudyTrackerApplication.sessionRepository)

    val subjects = subjectViewModel.subjects.observeAsState().value
    val subjectTimes = subjects?.map {
        sessionViewModel.calculateTimeForSubject(it.id).observeAsState().value
    }



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
        if (!subjects.isNullOrEmpty() && !subjectTimes.isNullOrEmpty()) {
            PieChartScreen(colors, subjects, subjectTimes)
            BarChartScreen(colors, subjects, subjectTimes)
        }

    }
}

@Composable
internal fun PieChartScreen(colors : List<Color>, subjects : List<Subject>,
                            inputs:List<Int?>) {

    //val inputValues = listOf(5,5)
    val inputTitles = subjects.map {
        it.name
    }
    val inputValues:List<Int> = inputs.map {
        if (it == null) {
            0
        } else {
            it
        }
    }

    val chartColors = colors.slice(IntRange(0, inputTitles.size))

    DonutChart(
        modifier = Modifier.padding(20.dp),
        colors = chartColors,
        inputValues = inputValues,
        inputTitles = inputTitles,
    )
}

@Composable
internal fun BarChartScreen(colors : List<Color>, subjects : List<Subject>,
                            inputs:List<Int?>) {

    val barChartInputsPercent = (0..10).map { (1..100).random().toFloat() }

    BarChart(
        modifier = Modifier.padding(20.dp),
        values = barChartInputsPercent
    )
}