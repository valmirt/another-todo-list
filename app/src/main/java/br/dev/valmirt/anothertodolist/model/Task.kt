package br.dev.valmirt.anothertodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Task (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: String = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(Date()),
    val isComplete: Boolean = false
)