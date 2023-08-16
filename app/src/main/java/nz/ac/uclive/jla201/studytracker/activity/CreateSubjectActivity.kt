package nz.ac.uclive.jla201.studytracker.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.subjectRepository
import nz.ac.uclive.jla201.studytracker.Subject
import nz.ac.uclive.jla201.studytracker.view_model.SubjectViewModel
import nz.ac.uclive.jla201.studytracker.ui.theme.StudyTrackerTheme

class CreateSubjectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SubjectFormScreenView();
                }
            }
        }
    }
}


@Composable
fun SubjectFormScreenView() {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
            .background(colorResource(id = R.color.white))
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
            ) {
                Text(
                    text = stringResource(R.string.create_subject),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp
                )

                var subjectNameInput by remember { mutableStateOf("") }
                TextField(
                    value = subjectNameInput,
                    onValueChange = { subjectNameInput = it },
                    label = { Text(stringResource(R.string.subject_name)) }
                )

                var subjectDescriptionInput by remember { mutableStateOf("") }
                TextField(
                    value = subjectDescriptionInput,
                    onValueChange = { subjectDescriptionInput = it },
                    label = { Text(stringResource(R.string.subject_description)) }
                )
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                    createSubject(subjectNameInput, subjectDescriptionInput, activity, context)
                }) {
                    Text(text = stringResource(R.string.submit),
                        fontSize = 20.sp)
                }
            }
        }
    }
}

fun createSubject(name: String, description: String, activity: Activity, context: Context) {
    val subjectViewModel = SubjectViewModel(subjectRepository)
    subjectViewModel.addSubject(Subject(name = name, description = description))

    val intent = Intent(context, MainActivity::class.java)
    activity.finish()
    context.startActivity(intent)
}