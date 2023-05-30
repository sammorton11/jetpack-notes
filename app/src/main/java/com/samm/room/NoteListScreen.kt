package com.samm.room

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLDecoder
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(
    navController: NavController,
    list: List<Notes>?,
    delete: (note: Notes) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            list?.let {
                itemsIndexed(it) { _, item ->

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {

                                val encodedTitle = Uri.encode(item.title)
                                val encodedNote = Uri.encode(item.note)

                                navController.navigate(
                                    "details-screen/${item.id}/${encodedTitle}/${encodedNote}"
                                )
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                        onClick = { delete(item) },
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Item Button"
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

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(
                onClick = { navController.navigate("create-note-screen") },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Go to Create Note Screen")
            }
        }
    }
}

