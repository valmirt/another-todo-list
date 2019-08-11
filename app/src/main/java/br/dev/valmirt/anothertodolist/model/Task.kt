package br.dev.valmirt.anothertodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Task (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: String = SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date()),
    val isComplete: Boolean = false
)