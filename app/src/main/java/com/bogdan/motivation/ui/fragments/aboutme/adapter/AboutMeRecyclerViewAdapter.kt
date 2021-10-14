package com.bogdan.motivation.ui.fragments.aboutme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.CommentsList
import com.bogdan.motivation.data.entities.local.FavQuotesList
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.databinding.ItemAboutMeBinding

@SuppressLint("NotifyDataSetChanged")
class AboutMeRecyclerViewAdapter :
    RecyclerView.Adapter<AboutMeRecyclerViewAdapter.AboutMeViewHolder>() {

    private val dataList = mutableListOf<String>()
    private var type = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutMeViewHolder {
        return AboutMeViewHolder(
            ItemAboutMeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AboutMeViewHolder, position: Int) {
        holder.bind(dataList[position], type, position)
    }

    override fun getItemCount() = dataList.count()

    fun setUserData(user: User) {

        dataList.clear()
        type = "1"
        dataList.addAll(
            listOf(
                user.name,
                user.age.toString(),
                user.sex,
                user.login ?: "Логин отсутсвует",
                user.password ?: "Пароль отсутсвует"
            )
        )
        notifyDataSetChanged()
    }

    fun setQuotesData(newQuotesList: FavQuotesList) {
        dataList.clear()
        type = "2"
        dataList.addAll(newQuotesList.favQuotes)
        notifyDataSetChanged()
    }

    fun setCommentsData(newCommentsList: CommentsList) {
        dataList.clear()
        type = "3"
        dataList.addAll(newCommentsList.comments)
        notifyDataSetChanged()
    }

    inner class AboutMeViewHolder(private val binding: ItemAboutMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, type: String, position: Int) {
            with(binding) {
                when (type) {
                    "1" -> {
                        tvAboutMe.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_account,
                            0,
                            0,
                            0
                        )
                        if (position == 4) {
                            val size = data.length
                            var password = ""
                            for (i in 0..size) {
                                password += "•"
                            }
                            tvAboutMe.text = password
                        } else {
                            tvAboutMe.text = data
                        }
                    }
                    "2" -> {
                        tvAboutMe.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_favorite_unpressed,
                            0,
                            0,
                            0
                        )
                        tvAboutMe.text = data
                    }
                    "3" -> {
                        tvAboutMe.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_comment,
                            0,
                            0,
                            0
                        )
                        tvAboutMe.text = data
                    }
                }
            }
        }
    }
}