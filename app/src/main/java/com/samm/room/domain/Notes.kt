package com.samm.room.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo("title")
    var title: String,
    @ColumnInfo("note")
    var note: String,
    @ColumnInfo("date")
    var date: String,
    var isChecked: Boolean = false
) : Parcelable
