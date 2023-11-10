package com.example.submissionintermediate.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Insert
import com.example.submissionintermediate.data.response.ListStoryItem

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(story:List<ListStoryItem>)

    @Query("SELECT * FROM stories")
    fun getAllStories():PagingSource<Int,ListStoryItem>

    @Query("DELETE FROM stories")
    fun deleteAll()
}