package hu.szurdok.todoapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.szurdok.todoapp.data.models.TaskCard

@Dao
interface TaskCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(taskCard: TaskCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(taskCard: List<TaskCard>)

    @Query("SELECT * FROM `taskCard`")
    fun load() : LiveData<List<TaskCard>>

    @Query("DELETE FROM `taskCard`")
    fun clearTable()
}