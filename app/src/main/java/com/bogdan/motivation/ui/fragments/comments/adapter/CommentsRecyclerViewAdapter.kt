package com.bogdan.motivation.ui.fragments.comments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.databinding.ItemCommentBinding

class CommentsRecyclerViewAdapter :
    RecyclerView.Adapter<CommentsRecyclerViewAdapter.CommentsViewHolder>() {

    private val commentsList = mutableListOf<String>()
    private var userName = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = commentsList.count()

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<String>) {
        commentsList.clear()
        commentsList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setName(name: String) {
        userName = name
    }

    inner class CommentsViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: String) {
            with(binding) {
                tvComment.text = comment
                tvName.text = userName
            }
        }
    }
}