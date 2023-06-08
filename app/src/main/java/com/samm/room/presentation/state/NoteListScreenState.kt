package com.samm.room.presentation.state

import com.samm.room.domain.Notes

data class NoteListScreenState(val list: List<Notes>? = null)