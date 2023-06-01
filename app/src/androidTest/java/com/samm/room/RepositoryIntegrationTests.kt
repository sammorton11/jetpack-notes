package com.samm.room

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryIntegrationTests {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val database = Room.inMemoryDatabaseBuilder(appContext, NoteDatabase::class.java).build()
    private val repository = RepositoryImpl(database.dao())

    @Test
    fun test_insert(): Unit = runBlocking {
        repository.deleteAllNotes()
        val emptyList = repository.getAllNotes().first()
        assert(emptyList.isEmpty())

        repository.insert(Notes(0L, "", "", ""))
        val notesList = repository.getAllNotes().first()
        assert(notesList.isNotEmpty())
    }

    @Test
    fun test_delete(): Unit = runBlocking {
        repository.insert(Notes(0L, "", "", ""))
        val notesList = repository.getAllNotes().first()
        assert(notesList.isNotEmpty())

        repository.deleteAllNotes()
        val emptyList = repository.getAllNotes().first()
        assert(emptyList.isEmpty())
    }
}