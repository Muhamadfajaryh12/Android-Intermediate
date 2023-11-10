package com.example.submissionintermediate

import com.example.submissionintermediate.data.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse() : List<ListStoryItem>{
        val items : MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100){
            val story = ListStoryItem(
                "My Photo URL Story $i",
                "My Story $i",
                "My Story Description $i ",
                "$i",
                0.1,
                0.1,
            )
            items.add(story)
        }
        return items
    }
}