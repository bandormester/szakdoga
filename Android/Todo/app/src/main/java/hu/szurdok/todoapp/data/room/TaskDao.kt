package hu.szurdok.todoapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.szurdok.todoapp.data.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(task: List<Task>)

    @Query("SELECT * FROM `task`")
    fun load() : LiveData<List<Task>>

    @Query("SELECT EXISTS(SELECT * FROM `task` WHERE id=:id)")
    fun isRowExists(id : Int) : Boolean

    @Query("DELETE FROM `task`")
    fun clearTable()

    @Query("SELECT * FROM `task` WHERE id=:id")
    fun loadById(id : Int) : LiveData<Task?>
}