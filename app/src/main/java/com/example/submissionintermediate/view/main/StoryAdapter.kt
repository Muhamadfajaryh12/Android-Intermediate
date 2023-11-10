package com.example.submissionintermediate.view.main
import androidx.core.util.Pair
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionintermediate.data.response.ListStoryItem
import com.example.submissionintermediate.databinding.StoryCardBinding
import com.example.submissionintermediate.view.detail.DetailActivity

class StoryAdapter(private val activity: Activity):PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding =
        StoryCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
        holder.itemView.setOnClickListener{
            val dataIntent = Intent(holder.itemView.context,DetailActivity::class.java)
            dataIntent.putExtra(DetailActivity.EXTRA_ID,item?.id)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.ivImage, "picture"),
                    Pair(holder.binding.tvName, "title"),
                    Pair(holder.binding.tvDescription, "description"),
                )
            holder.itemView.context.startActivity(dataIntent,optionsCompat.toBundle())
        }
    }
    class MyViewHolder(val binding:StoryCardBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item:ListStoryItem){
            binding.apply{
            Glide.with(itemView.context)
                .load(item.photoUrl)
                .into(binding.ivImage)
                binding.tvName.text=item.name
                binding.tvDescription.text=item.description
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem:ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem:ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}