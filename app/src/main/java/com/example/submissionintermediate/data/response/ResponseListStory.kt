package com.example.submissionintermediate.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseListStory(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList()
)

@Entity(tableName = "stories")
data class ListStoryItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat:Double? = null,

	@field:SerializedName("lon")
	val lon:Double? = null

)
