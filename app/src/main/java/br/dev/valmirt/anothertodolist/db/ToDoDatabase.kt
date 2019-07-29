package br.dev.valmirt.anothertodolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.dev.valmirt.anothertodolist.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}