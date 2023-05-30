package com.samm.room

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.samm.room.ui.theme.RoomTheme

/*
 *  Todo:
 *   - Actions Menu:
 *      - Sort list by date or title
 *   - Icon buttons for each card to delete the note -- provide warning dialog
 *   - Set reminder for task button in details page?
 *
 */

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = NoteDatabase.getDatabase(this)
        val repository = RepositoryImpl(database.dao())
        val factory = NoteViewModel.Factory(repository)
        val viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        viewModel.notesList()

        setContent {
            RoomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {
                            // Move this to its own file
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Jetpack Notes",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background
                                ),
                                actions = {
                                    var expandedOptionsMenu by remember {
                                        mutableStateOf(false)
                                    }
                                    IconButton(onClick = {
                                        expandedOptionsMenu = !expandedOptionsMenu
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "",
                                            tint = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expandedOptionsMenu,
                                        onDismissRequest = { expandedOptionsMenu = false },
                                        modifier = Modifier
                                            .semantics { testTag = "Options Menu Drop Down" },
                                        offset = DpOffset(0.dp, 12.dp),
                                        content = {
                                            DropdownMenuItem(
                                                text = { Text(text = "Clear list") },
                                                modifier = Modifier
                                                    .semantics { testTag = "Clear list button" },
                                                onClick = {
                                                    viewModel.deleteAllNotes()
                                                },
                                                colors = MenuDefaults.itemColors(
                                                    textColor = MaterialTheme.colorScheme.tertiary
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.padding(it)
                        ){
                            AppNavigation(viewModel)
                        }
                    }
                }
            }
        }
    }
}
