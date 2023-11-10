package com.example.submissionintermediate.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailStory(
	@field:SerializedName("story")
	val story: Story? = null
)

data class Story(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat:Double? = null,

	@field:SerializedName("lon")
	val lon:Double? = null
)
