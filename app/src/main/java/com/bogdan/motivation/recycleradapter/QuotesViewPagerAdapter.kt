package com.bogdan.motivation.recycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.R
import com.bogdan.motivation.`interface`.OnClickListener
import com.bogdan.motivation.databinding.FragmentSingleMotivationBinding
import com.bogdan.motivation.entities.Quote

class QuotesViewPagerAdapter :
    RecyclerView.Adapter<QuotesViewPagerAdapter.ViewHolder>() {

    private val quotesList = mutableListOf<Quote>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSingleMotivationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = quotesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quotesList[position])
    }

    fun setData(newList: List<Quote>) {
        quotesList.clear()
        quotesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: FragmentSingleMotivationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            with(binding) {
                textQuote.text = quote.quote
                textAuthor.text = quote.author

                if (quote.isFavorite) {
                    imageLike.setImageResource(R.drawable.ic_like_pressed)
                } else {
                    imageLike.setImageResource(R.drawable.ic_like_unpressed)
                }

                imageLike.setOnClickListener {

                    quote.changeFavorite()
                    onClickListener.onFavoriteClickListener(quote.isFavorite, quote.quote)

                    if (quote.isFavorite) {
                        imageLike.setImageResource(R.drawable.ic_like_pressed)
                    } else {
                        imageLike.setImageResource(R.drawable.ic_like_unpressed)
                    }
                }

                imageShare.setOnClickListener {
                    onClickListener.onShareClickListener(quote.quote, quote.author)
                }
            }
        }
    }
}