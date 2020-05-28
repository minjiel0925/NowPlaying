package org.example.mjworkspace.myapplication.fragments.m.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.mjworkspace.myapplication.fragments.m.model.Now


@Dao
interface NowDao {

    @Query("SELECT * FROM now ORDER BY releaseDate DESC")
    fun getNow(): LiveData<List<Now>>

    @Query("SELECT * FROM now ORDER BY releaseDate DESC")
    fun getPagedNow(): DataSource.Factory<Int, Now>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNow(nowlist: List<Now>)
}