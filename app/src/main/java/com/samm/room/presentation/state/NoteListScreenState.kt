package com.samm.room.presentation.state

import com.samm.room.domain.Notes

data class NoteListScreenState(var list: List<Notes> = emptyList())