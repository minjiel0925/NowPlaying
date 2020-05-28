package org.example.mjworkspace.myapplication.fragments.m.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.mjworkspace.myapplication.fragments.m.model.Now

@Database(entities = [Now::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun nowDao(): NowDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): MyDatabase {
            return Room
                .databaseBuilder(context, MyDatabase::class.java, "mydb")
                .build()
        }
    }
}

