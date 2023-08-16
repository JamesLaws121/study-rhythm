package nz.ac.uclive.jla201.studytracker.component

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import nz.ac.uclive.jla201.studytracker.activity.CreateSubjectActivity
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.Session
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.sessionRepository
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.subjectRepository
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.activity.CreateSessionActivity
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(){
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val subjectViewModel = SubjectViewModel(subjectRepository)
    val subjects = subjectViewModel.subjects.observeAsState().value

    val sessionViewModel = SessionViewModel(sessionRepository)
    val sessions = sessionViewModel.sessions.observeAsState().value


    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())
        .height(IntrinsicSize.Max)
        .background(colorResource(id = R.color.white))
        .wrapContentSize(Alignment.Center))
    {

        Text(
            text = context.getString(R.string.app_name),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        Spacer(modifier = Modifier.height(50.dp))


        Text(
            text = stringResource(R.string.your_subjects),
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        if (!subjects.isNullOrEmpty()) {
            SubjectList(subjects)
        } else {
            Text(
                text = stringResource(R.string.no_subjects_found),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                switchToSubjectActivity(context)
            }) {
            Text(modifier = Modifier
                .width(200.dp),
                text = stringResource(R.string.add_subject),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }


        Spacer(modifier = Modifier.height(50.dp))


        Text(
            text = stringResource(R.string.work_sessions),
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(30.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )


        if (!subjects.isNullOrEmpty()) {
            if (!sessions.isNullOrEmpty()) {
                SessionList(sessions, subjectViewModel)
            } else {
                Text(
                    text = stringResource(R.string.no_sessions_found),
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    switchToSessionActivity(context)
                }) {
                Text(modifier = Modifier
                    .width(200.dp),
                    text = stringResource(R.string.log_work_session),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                text = stringResource(R.string.no_subjects_to_log_for),
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
    LazyColumn(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(10.dp)
        .height(125.dp)
        .border(1.dp, Color.Black)) {
        items(subjects) { subject ->
            Box(
                Modifier
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
fun SessionList(sessions: List<Session>, subjectViewModel: SubjectViewModel) {
    LazyColumn( modifier = Modifier
        .border(1.dp, Color.Black)
        .verticalScroll(rememberScrollState())
        .padding(10.dp)
        .height(125.dp)) {
        items(sessions) { session ->
            Box(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                val subject = subjectViewModel.getSubject(session.subjectId).observeAsState().value?.name
                Text(
                    text = subject + "   " + session.date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "   " +  session.duration + " hours",
                    fontSize = 20.sp,
                )
            }
        }
    }
}

fun switchToSubjectActivity(context: Context) {
    val intent = Intent(context, CreateSubjectActivity::class.java)
    context.startActivity(intent)
}

fun switchToSessionActivity(context: Context) {
    val intent = Intent(context, CreateSessionActivity::class.java)
    context.startActivity(intent)
}
