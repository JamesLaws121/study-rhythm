package nz.ac.uclive.jla201.studytracker.component

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Composable
fun StatisticsScreen() {
    //val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(StudyTrackerApplication.subjectRepository)
    val sessionViewModel = SessionViewModel(StudyTrackerApplication.sessionRepository)

    val subjects = subjectViewModel.subjects.observeAsState().value
    val subjectTimes = subjects?.map {
        sessionViewModel.calculateTimeForSubject(it.id).observeAsState().value
    }

    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

    val days = arrayListOf<LocalDate>()
    for (i in 0..6) {
        days.add(calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        calendar.add(Calendar.DAY_OF_WEEK, 1)
    }

    val dateTimes =  days.map {
        sessionViewModel.calculateTimeForDate(it.toEpochDay()).observeAsState().value
    }



    val colors = listOf(
        Color(0xff6050dc),
        Color(0xffc435c0),
        Color(0xfffc2296),
        Color(0xffff4567),
        Color(0xffff773a),
        Color(0xffffa600),
        Color(0xffEAC435)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

        Text(
            text = stringResource(R.string.statistics),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        if (!subjects.isNullOrEmpty() && !subjectTimes.isNullOrEmpty()) {
            PieChartScreen(colors, subjects, subjectTimes)
            BarChartScreen(colors, dateTimes)
        }
    }
    if (!subjects.isNullOrEmpty() && !subjectTimes.isNullOrEmpty()) {
        Button(
            onClick = {
                sendStats(context, subjects, subjectTimes)
            }) {
            Text(modifier = Modifier
                .width(200.dp),
                text = "Share your stats",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Text(text = stringResource(R.string.you_haven_t_got_any_stats_to_share))
    }
    
}

@Composable
internal fun PieChartScreen(colors : List<Color>, subjects : List<Subject>,
                            inputs:List<Float?>) {

    val inputTitles = subjects.map {
        it.name
    }
    val inputValues:List<Float> = inputs.map {
        it ?: 0F
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
internal fun BarChartScreen(colors : List<Color>, inputs:List<Int?>) {

    val inputValues:List<Float> = inputs.map {
        it?.toFloat() ?: 0F
    }

    BarChart(
        modifier = Modifier.padding(20.dp),
        values = inputValues,
        colors = colors,
        maxHeight = 200
    )
}

@Composable
fun summaryScreen(colors : List<Color>, subjects : List<Subject>,
                  subjectTimes:List<Float?>) {
    //TODO
}


fun sendStats(context: Context, subjects : List<Subject>,
              subjectTimes:List<Float?>) {

    val inputTitles = subjects.map {
        it.name
    }
    val inputValues:List<Float> = subjectTimes.map {
        it ?: 0F
    }
    val timeValues:List<String> = inputValues.map {
        it.toInt().toString() + " " + context.getString(R.string.hours) + " " +
                ((it - it.toInt())*60).toInt().toString() + " " + context.getString(R.string.minutes)
    }

    var message = context.getString(R.string.check_out_my_studyrhythm_stats)

    timeValues.forEachIndexed { index, value ->
        message += inputTitles[index] + " " + value + "\n"
    }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}