package hu.szurdok.todoapp.data.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao{
    @Insert(onConflict = REPLACE)
    fun save(user : User)

    @Query("SELECT * FROM `user`")
    fun load() : LiveData<List<User>>
}