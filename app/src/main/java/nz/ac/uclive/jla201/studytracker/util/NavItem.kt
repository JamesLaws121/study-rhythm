package nz.ac.uclive.jla201.studytracker.util

import nz.ac.uclive.jla201.studytracker.R


sealed class NavItem(var title:String, var icon:Int, var screen_route:String){
    object Home : NavItem("Home", R.drawable.ic_home,"home")
    object Profile: NavItem("Profile", R.drawable.ic_profile,"profile")
    object Settings: NavItem("Settings", R.drawable.ic_settings,"settings")
    object Statistics: NavItem("Statistics", R.drawable.ic_statistics,"statistics")
}