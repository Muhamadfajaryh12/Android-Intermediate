package com.example.submissionintermediate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionintermediate.data.response.ListStoryItem


@Database(
    entities = [ListStoryItem::class,RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase:RoomDatabase() {

    abstract fun storyDao():StoryDao
    abstract fun remoteKeyDao():RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "stories_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}