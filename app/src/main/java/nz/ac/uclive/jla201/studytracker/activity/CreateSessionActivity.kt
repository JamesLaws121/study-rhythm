package nz.ac.uclive.jla201.studytracker.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.sessionRepository
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.subjectRepository
import nz.ac.uclive.jla201.studytracker.ui.theme.StudyTrackerTheme
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class CreateSessionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SessionFormScreenView();
                }
            }
        }
    }
}


@Composable
fun SessionFormScreenView() {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(subjectRepository)
    val subjects = subjectViewModel.subjects.observeAsState().value


    Column(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.TopCenter)
            .background(colorResource(id = R.color.white))
            .verticalScroll(rememberScrollState())
            .height(IntrinsicSize.Max)
    ) {
        Row(modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(colorResource(id = R.color.white))
        ) {
            Button(
                onClick = { returnToMain(activity, context) }
            ) {
                Text("Back")
            }
        }

        Row(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(colorResource(id = R.color.white))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(20.dp)
            ) {



                Text(modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(10.dp),
                    text = "Create session",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp
                )


                var selectedSubject by remember { mutableStateOf(-1) }
                Column(modifier = Modifier
                    .height(200.dp)
                    .width(400.dp)) {
                    Text(
                        text = "Choose Subject",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )

                    if (!subjects.isNullOrEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .wrapContentSize(Alignment.Center)
                                .verticalScroll(rememberScrollState())
                                .heightIn(25.dp, 175.dp)
                                .fillMaxWidth()
                        ) {
                            items(subjects) { subject ->
                                val selected = selectedSubject == subject.id
                                Box(
                                    Modifier
                                        .border(1.dp, Color.Black)
                                        .fillMaxWidth()
                                        .background(if (selected) Color.Cyan else Color.White)
                                        .selectable(
                                            selected = selected,
                                            onClick = { selectedSubject = subject.id }
                                        )
                                ) {
                                    Text(
                                        modifier = Modifier.padding(10.dp),
                                        text = subject.name,
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp
                                    )
                                }

                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            text = "No subjects found",
                            color = Color.Cyan,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    }
                }

                var dateInput by remember { mutableStateOf(LocalDate.now()) }
                var startTimeInput by remember { mutableStateOf(LocalTime.now()) }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)) {

                    Text(
                        text = "Date & Time",
                        fontSize = 25.sp
                    )

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)) {
                        val calendar = Calendar.getInstance()
                        val hour = calendar[Calendar.HOUR_OF_DAY]
                        val minute = calendar[Calendar.MINUTE]
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        val day = calendar.get(Calendar.DAY_OF_MONTH)

                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                dateInput = LocalDate.of(year, month, dayOfMonth)
                            }, year, month, day
                        )
                        Button(onClick = {
                            datePickerDialog.show()
                        }) {
                            Text(
                                text = "" + dateInput,
                                fontSize = 15.sp
                            )
                        }


                        val mTimePickerDialog = TimePickerDialog(
                            context,
                            { _, hour: Int, minute: Int ->
                                startTimeInput = LocalTime.of(hour, minute)
                            }, hour, minute, false
                        )

                        Button(
                            onClick = { mTimePickerDialog.show() }
                        ) {
                            Text(
                                "%d : %d".format(startTimeInput.hour, startTimeInput.minute),
                                fontSize = 15.sp
                            )
                        }
                    }
                }


                var descriptionInput by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    value = descriptionInput,
                    onValueChange = { descriptionInput = it },
                    label = { Text("Session Description") }
                )

                var durationInput by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    value = durationInput,

                    onValueChange = { value ->
                        if (value.length <= 2) {
                            durationInput = value.filter { it.isDigit() }
                        }
                    },
                    label = { Text("Duration") }
                )

                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                    createSession(descriptionInput, dateInput , startTimeInput,
                        durationInput, selectedSubject, activity, context
                    )
                }) {
                    Text(text = "Submit",
                        fontSize = 25.sp)
                }
            }
        }
    }
}



private fun createSession(description: String, startDate: LocalDate, startTime : LocalTime,
                          durationString: String, subjectId: Int, activity: Activity, context: Context) {
    val sessionViewModel = SessionViewModel(sessionRepository)


    //val durationFormat = DateTimeFormatter.ofPattern("hh:mm:ss")
    //val durationTime = LocalTime.parse(durationString).toNanoOfDay()
    val durationTime = durationString.toLong()

    sessionViewModel.addSession(Session(description = description, date = startDate,
        start = startTime.toNanoOfDay(), duration = durationTime, subjectId = subjectId))

    val intent = Intent(context, MainActivity::class.java)
    activity.finish()
    context.startActivity(intent)
}