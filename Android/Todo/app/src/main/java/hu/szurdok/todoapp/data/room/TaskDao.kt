package hu.szurdok.todoapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.szurdok.todoapp.data.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(task: List<Task>)

    @Query("SELECT * FROM `task`")
    fun load() : LiveData<List<Task>>

    @Query("SELECT * FROM `task` WHERE id=:id")
    fun loadById(id : Int) : LiveData<Task?>

    @Query("DELETE FROM `task` WHERE id=:id")
    fun delete(id : Int)
}