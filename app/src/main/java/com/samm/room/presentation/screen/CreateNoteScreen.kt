package com.samm.room.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.room.domain.Notes
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(insert: (item: Notes) -> Unit, navController: NavController) {

    val titleTextFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val noteTextFieldValue = remember { mutableStateOf(TextFieldValue()) }

    val currentDate = LocalDate.now()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)) {

        Text(text = "Title")
        OutlinedTextField(
            value = titleTextFieldValue.value,
            onValueChange = { titleTextFieldValue.value = it },
            label = { Text("Title...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Note")
        OutlinedTextField(
            value = noteTextFieldValue.value,
            onValueChange = { noteTextFieldValue.value = it },
            label = { Text("Add a note...") },
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                insert(
                    Notes(
                    title = titleTextFieldValue.value.text,
                    note = noteTextFieldValue.value.text,
                    date = currentDate.toString()
                )
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }
    }
}