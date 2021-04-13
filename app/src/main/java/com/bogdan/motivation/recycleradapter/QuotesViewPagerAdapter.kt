package com.bogdan.motivation.recycleradapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityMotivationSingleBinding
import com.bogdan.motivation.entities.Quote

class QuotesViewPagerAdapter(private val quotesList: List<Quote>) :
    RecyclerView.Adapter<QuotesViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ActivityMotivationSingleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun getItemCount() = quotesList.count()

    //TODO Как обращаться в бд без контекста? Так же использовать метод startActivity вне активити класса
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = quotesList[position]
        holder.textQuote.text = listItem.quote
        holder.textAuthor.text = listItem.author

        val isFavorite = dbManager.readFavoriteFromQuotesDb(listItem.quote)

        if (isFavorite == "0") {
            holder.imageLike.setImageResource(R.drawable.ic_like_unpressed)

        } else {
            holder.imageLike.setImageResource(R.drawable.ic_like_pressed)
        }

        holder.imageLike.setOnClickListener {

            val isFavoriteClicked = dbManager.readFavoriteFromQuotesDb(listItem.quote)

            if (isFavoriteClicked == "0") {
                holder.imageLike.setImageResource(R.drawable.ic_like_pressed)

                dbManager.insertFavoriteToQuotesDb("1", listItem.quote)
            } else {
                holder.imageLike.setImageResource(R.drawable.ic_like_unpressed)

                dbManager.insertFavoriteToQuotesDb("0", listItem.quote)
            }
        }

        holder.imageShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, listItem.quote + " " + listItem.author)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    inner class ViewHolder(binding: ActivityMotivationSingleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textQuote = binding.textQuote
        val textAuthor = binding.textAuthor
        var imageShare = binding.imageShare
        var imageLike = binding.imageLike
    }
}