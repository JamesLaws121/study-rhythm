package nz.ac.uclive.jla201.studytracker.activity

import android.R.id.content
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.BottomNavigation;
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import nz.ac.uclive.jla201.studytracker.R
import nz.ac.uclive.jla201.studytracker.StudyTrackerApplication
import nz.ac.uclive.jla201.studytracker.component.HomeScreen
import nz.ac.uclive.jla201.studytracker.util.NavItem
import nz.ac.uclive.jla201.studytracker.component.SettingsScreen
import nz.ac.uclive.jla201.studytracker.component.StatisticsScreen
import nz.ac.uclive.jla201.studytracker.ui.theme.StudyTrackerTheme
import nz.ac.uclive.jla201.studytracker.view_model.PreferencesViewModel
import nz.ac.uclive.jla201.studytracker.view_model.SessionViewModel
import java.time.ZoneId
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenView();
                }
            }
        }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window,
                window.decorView.findViewById(content)).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())

                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val sessionViewModel = SessionViewModel(StudyTrackerApplication.sessionRepository)
    val preferencesViewModel = PreferencesViewModel(StudyTrackerApplication.preferencesRepository)
    val deleteSessions = preferencesViewModel.getDeletePreferences().observeAsState().value
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    if (deleteSessions == 1) {
        sessionViewModel.removeOldSessions((calendar.time.toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate().toEpochDay()))
    }


    val navController = rememberNavController();
    NavBarMap (navController);
    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .weight(2f)
                .wrapContentHeight(Alignment.Bottom)
                .padding(all = 2.dp),
        ) {
            BottomNavigation(navController);
        }
    }
}


@Composable
fun BottomNavigation(navController: NavController) {
    NavItem.Settings.title = stringResource(R.string.settings)
    NavItem.Home.title = stringResource(R.string.home)
    NavItem.Statistics.title = stringResource(R.string.statistics)

    val navItems = listOf(
        NavItem.Settings,
        NavItem.Home,
        NavItem.Statistics
    )
    BottomNavigation(modifier = Modifier.height(50.dp), backgroundColor = MaterialTheme.colorScheme.background) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Cyan,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavBarMap(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.screen_route) {
        composable(NavItem.Home.screen_route) {
            HomeScreen();
        }
        composable(NavItem.Settings.screen_route) {
            SettingsScreen();
        }
        composable(NavItem.Statistics.screen_route) {
            StatisticsScreen();
        }
    }
}



fun returnToMain(activity: Activity, context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    activity.finish()
    context.startActivity(intent)
}
