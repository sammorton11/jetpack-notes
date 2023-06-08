package com.samm.room.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samm.room.domain.Notes
import com.samm.room.domain.Repository
import com.samm.room.presentation.state.NoteListScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: Repository): ViewModel() {

    val _state = mutableStateOf(NoteListScreenState())
    val state: State<NoteListScreenState> = _state

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repo: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoteViewModel(repo) as T
        }
    }

    private fun getAllNotes(): Flow<List<Notes>> {
        return repository.getAllNotes()
    }

    fun notesList() = viewModelScope.launch(Dispatchers.IO) {
        getAllNotes().collect {
            _state.value = NoteListScreenState(list = it)
        }
    }

    fun getNoteById(id: Long?): Flow<Notes?> {
        return repository.getNoteById(id)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.getDao().deleteAllNotes()
    }

    fun insert(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.getDao().insert(note)
    }

    fun delete(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.getDao().delete(note)
    }

    fun update(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun customDecode(encodedText: String): String {
        val regex = "%([0-9A-Fa-f]{2})".toRegex()
        return regex.replace(encodedText) { matchResult ->
            val hexValue = matchResult.groupValues[1]
            val decodedChar = hexValue.toInt(16).toChar()
            decodedChar.toString()
        }
    }
}