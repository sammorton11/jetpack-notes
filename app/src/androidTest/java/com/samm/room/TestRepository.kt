package com.samm.room

import com.samm.room.data.Dao
import com.samm.room.domain.Notes
import com.samm.room.domain.Repository
import kotlinx.coroutines.flow.Flow

class TestRepository(private val dao: Dao): Repository {

    override fun getDao(): Dao {
        return dao
    }

    override fun getAllNotes(): Flow<List<Notes>> {
        return dao.getAllNotes()
    }

    override fun getNoteById(noteId: Long?): Flow<Notes?> {
        return dao.getAllNoteById(noteId)
    }

    override fun deleteAllNotes() {
        return dao.deleteAllNotes()
    }

    override suspend fun update(note: Notes) {
        dao.update(note)
    }

    override suspend fun insert(note: Notes) {
        dao.insert(note)
    }

    override suspend fun delete(note: Notes) {
        dao.delete(note)
    }
}