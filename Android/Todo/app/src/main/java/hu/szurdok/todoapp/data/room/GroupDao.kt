package hu.szurdok.todoapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hu.szurdok.todoapp.data.models.Group

@Dao
interface GroupDao{
    @Insert(onConflict = REPLACE)
    fun save(group: Group)

    @Insert(onConflict = REPLACE)
    fun saveAll(group : List<Group>)

    @Query("SELECT * FROM `group`")
    fun load() : LiveData<List<Group>>

    @Query("SELECT * FROM `group` WHERE id=:groupId")
    fun loadById(groupId: Int) : LiveData<Group>
}