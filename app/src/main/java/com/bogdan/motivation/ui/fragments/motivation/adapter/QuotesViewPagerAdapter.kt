package com.bogdan.motivation.ui.fragments.motivation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.databinding.FragmentSingleMotivationBinding

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

                btnLike.isSelected = quote.isFavorite

                btnLike.setOnClickListener {
                    quote.isFavorite = !quote.isFavorite
                    onClickListenerMotivation.onFavoriteClickListener(quote)
                    it.isSelected = !it.isSelected
                }

                btnShare.setOnClickListener {
                    onClickListenerMotivation.onShareClickListener(quote)
                }
            }
        }
    }
}
