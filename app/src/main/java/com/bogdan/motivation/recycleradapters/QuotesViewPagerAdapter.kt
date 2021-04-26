package com.bogdan.motivation.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentSingleMotivationBinding
import com.bogdan.motivation.entities.Quote
import com.bogdan.motivation.interfaces.OnClickListenerMotivation

class QuotesViewPagerAdapter :
    RecyclerView.Adapter<QuotesViewPagerAdapter.QuotesViewHolder>() {

    private val quotesList = mutableListOf<Quote>()
    lateinit var onClickListenerMotivation: OnClickListenerMotivation

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        return QuotesViewHolder(
            FragmentSingleMotivationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = quotesList.count()

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(quotesList[position])
    }

    fun setData(newList: List<Quote>) {
        quotesList.clear()
        quotesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class QuotesViewHolder(private val binding: FragmentSingleMotivationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            with(binding) {
                tvQuote.text = quote.quote
                tvAuthor.text = quote.author

                if (quote.isFavorite) {
                    btnLike.setImageResource(R.drawable.ic_like_pressed)
                } else {
                    btnLike.setImageResource(R.drawable.ic_like_unpressed)
                }

                btnLike.setOnClickListener {
                    quote.changeFavorite()
                    onClickListenerMotivation.onFavoriteClickListener(quote.isFavorite, quote.quote)

                    if (quote.isFavorite) {
                        btnLike.setImageResource(R.drawable.ic_like_pressed)
                    } else {
                        btnLike.setImageResource(R.drawable.ic_like_unpressed)
                    }
                }

                btnShare.setOnClickListener {
                    onClickListenerMotivation.onShareClickListener(quote.quote, quote.author)
                }
            }
        }
    }
}
