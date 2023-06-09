package com.samm.room.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samm.room.domain.NotificationScheduler
import com.samm.room.presentation.screen.CreateNoteScreen
import com.samm.room.presentation.screen.DetailsScreen
import com.samm.room.presentation.screen.NoteListScreen
import com.samm.room.presentation.viewmodel.NoteViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    val notificationWorker = NotificationScheduler(LocalContext.current.applicationContext)

    NavHost(navController = navController, startDestination = "notes-screen") {

        composable("notes-screen") {
            val list = viewModel.state.value.list
            NoteListScreen(
                navController = navController,
                list = list,
                delete = viewModel::delete
            )
        }

        composable("create-note-screen") {
            CreateNoteScreen(
                insert = viewModel::insert,
                scheduleNotification = notificationWorker::scheduleNotification,
                navController = navController
            )
        }

        composable("details-screen/{noteId}/{title}/{details}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.LongType },
                navArgument("details") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType}
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getLong("noteId")
            val title = backStackEntry.arguments?.getString("title")
            val details = backStackEntry.arguments?.getString("details")
            val note by viewModel.getNoteById(noteId).collectAsState(initial = null)

            DetailsScreen(
                note = note,
                title = title,
                details = details,
                customDecode = viewModel::customDecode,
                update = viewModel::update,
                navController = navController
            )
        }
    }
}