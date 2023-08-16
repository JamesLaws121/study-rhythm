package nz.ac.uclive.jla201.studytracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.view_model.PreferencesViewModel
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication.Companion.preferencesRepository

@Composable
fun SettingsScreen() {
    val preferencesViewModel = PreferencesViewModel(preferencesRepository)
    var deleteSessions = preferencesViewModel.getDeletePreferences().observeAsState().value
    var sessionLife = preferencesViewModel.getSessionLife().observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(R.string.settings),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(R.string.remove_old_sessions),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start),
            textAlign = TextAlign.Center,
            fontSize = 15.sp
        )
        Text(
            text = stringResource(R.string.sessions_will_be_removed_each_week),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start),
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
        Switch(
            checked = deleteSessions == 1,
            onCheckedChange = { preferencesViewModel.saveDeletePreferences(it.compareTo(false));
                deleteSessions = it.compareTo(false)
            }
        )
    }
}