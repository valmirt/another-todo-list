package br.dev.valmirt.anothertodolist.db

import androidx.room.*
import br.dev.valmirt.anothertodolist.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    suspend fun findAll (): List<Task>

    @Query("SELECT * FROM Task t WHERE t.id = :id")
    suspend fun findById (id: String): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save (task: Task)

    @Update
    suspend fun update (task: Task) : Int

    @Query("UPDATE task SET isComplete = NOT isComplete WHERE id = :id")
    suspend fun updateCompleted (id: String)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun deleteById (id: String): Int

    @Query("DELETE FROM Task")
    suspend fun deleteAll ()

    @Query("DELETE FROM Task WHERE isComplete = 1")
    suspend fun deleteAllCompleted (): Int
}