package nz.ac.uclive.jla201.studytracker.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalProvider
import nz.ac.uclive.jla201.studytracker.activity.CreateSubjectActivity
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.sessionRepository
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.subjectRepository
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.activity.CreateSessionActivity
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel


@Composable
fun HomeScreen(){
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(subjectRepository)
    val subjects = subjectViewModel.subjects.observeAsState().value

    val sessionViewModel = SessionViewModel(sessionRepository)
    val sessions = sessionViewModel.sessions.observeAsState().value

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        Text(
            text = "Your subjects",
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        if (subjects != null) {
            SubjectList(subjects)
        } else {
            Text(
                text = "No subjects found",
                color = Color.Cyan,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { switchToSubjectActivity(context, activity)
        }) {
            Text(text = "Add subject",
                fontSize = 20.sp)
        }

        Text(
            text = "Work sessions",
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        if (sessions != null) {
            SessionList(sessions)
        } else {
            Text(
                text = "No sessions found",
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        if (subjects != null) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { switchToSessionActivity(context, activity)
            }) {
                Text(text = "Log work session",
                    fontSize = 20.sp)
            }
        } else {
            Text(
                text = "No subjects to log for",
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

    }
}


@Composable
fun SubjectList(subjects: List<Subject>) {
    LazyColumn {
        items(subjects) { subject ->
            Box(Modifier
                .border(1.dp, Color.Black)
                .padding(10.dp)
                .fillMaxWidth()){
                Text(
                    text = subject.name.orEmpty(),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
fun SessionList(sessions: List<Session>) {
    LazyColumn {
        items(sessions) { session ->
            Box(
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = session.name.orEmpty(),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

fun switchToSubjectActivity(context: Context, activity: Activity) {
    val intent = Intent(context, CreateSubjectActivity::class.java)
    activity.finish()
    context.startActivity(intent)
}

fun switchToSessionActivity(context: Context, activity: Activity) {
    val intent = Intent(context, CreateSessionActivity::class.java)
    activity.finish()
    context.startActivity(intent)
}
