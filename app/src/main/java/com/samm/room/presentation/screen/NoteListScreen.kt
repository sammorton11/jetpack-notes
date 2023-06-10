package com.samm.room.presentation.screen

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samm.room.domain.Notes

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(
    selectedNotes: List<Notes>,
    toggleNoteSelection: (item: Notes, checked: Boolean) -> Unit,
    navController: NavController,
    list: List<Notes>?
) {
    var visibleCardIndices by remember { mutableStateOf(listOf<Int>()) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val reversedList = list?.reversed()
            reversedList?.let { list ->
                itemsIndexed(list) { index, item ->

                    val isVisible = index in visibleCardIndices

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    val encodedTitle = Uri.encode(item.title)
                                    var encodedNote = Uri.encode(item.note)
                                    if (encodedNote.isBlank()) {
                                        encodedNote = "Empty"
                                    }

                                    navController.navigate(
                                        "details-screen/${item.id}/${encodedTitle}/${encodedNote}"
                                    )
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier.align(Alignment.CenterStart),
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                                        Checkbox(
                                            modifier = Modifier.padding(end = 5.dp),
                                            checked = selectedNotes.contains(item),
                                            onCheckedChange = { checked ->
                                                toggleNoteSelection(item, checked)
                                                if (checked && !isVisible) {
                                                    visibleCardIndices = visibleCardIndices + index
                                                } else if (!checked && isVisible) {
                                                    visibleCardIndices = visibleCardIndices - index
                                                }
                                            }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                                Text(
                                    text = item.note,
                                    modifier = Modifier.sizeIn(maxHeight = 300.dp),
                                    fontSize = 18.sp,
                                    lineHeight = 25.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.padding(top = 15.dp))

                                Text(text = item.date)
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(
                onClick = { navController.navigate("create-note-screen") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Go to Create Note Screen"
                )
            }
        }
    }

    // Initialize the visibleCardIndices list with the indices of all cards initially visible
    visibleCardIndices = list?.indices?.toList() ?: emptyList()
}

