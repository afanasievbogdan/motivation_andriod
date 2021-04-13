package com.bogdan.motivation.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityFavoriteMotivationBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote
import java.util.*

class FavoriteMotivationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteMotivationBinding
    private val dbManager = DBManager(applicationContext)
    private var quotesList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMotivationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbManager.openDb()
        dbManager.readFavouriteQuoteFromQuotesDb()
        onClickCategoriesSelection()

        binding.viewPager2Fav.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager2Fav.adapter = MyViewPager2AdapterFav(quotesList)

    }

    //TODO вьюхолдер может быть вложенным классом адаптера, но не активити
    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textQuote: TextView = itemView.findViewById(R.id.textQuote)
        val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        var imageShare: ImageButton = itemView.findViewById(R.id.imageShare)
        var imageLike: ImageButton = itemView.findViewById(R.id.imageLike)
    }

    //TODO адаптер должен быть отдельным классом обязательно
    inner class MyViewPager2AdapterFav(private val quotesValue: List<Quote>) :
        RecyclerView.Adapter<MyViewHolderFav>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_motivation_single, parent, false)
            val myViewHolder = MyViewHolderFav(view)
            val position = myViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                myViewHolder.textQuote.setOnClickListener {
                    Toast.makeText(parent.context, myViewHolder.adapterPosition, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            return myViewHolder
        }

        override fun getItemCount(): Int {
            return quotesValue.count()
        }

        //TODO обращайся к БД в активити/фрагменте, а в адаптер передавай готовый лист
        override fun onBindViewHolder(holderFav: MyViewHolderFav, position: Int) {
            val listItem = quotesValue[holderFav.adapterPosition]
            holderFav.textQuote.text = listItem.quote
            holderFav.textAuthor.text = listItem.author

            val isFavorite = dbManager.readFavoriteFromQuotesDb(listItem.quote)

            if (isFavorite == "0") {
                holderFav.imageLike.setImageResource(R.drawable.ic_like_unpressed)

            } else {
                holderFav.imageLike.setImageResource(R.drawable.ic_like_pressed)
            }

            holderFav.imageLike.setOnClickListener {

                val isFavoriteClicked = dbManager.readFavoriteFromQuotesDb(listItem.quote)

                if (isFavoriteClicked == "0") {
                    holderFav.imageLike.setImageResource(R.drawable.ic_like_pressed)

                    dbManager.insertFavoriteToQuotesDb("1", listItem.quote)
                } else {
                    holderFav.imageLike.setImageResource(R.drawable.ic_like_unpressed)

                    dbManager.insertFavoriteToQuotesDb("0", listItem.quote)
                }
            }

            holderFav.imageShare.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, listItem.quote + " " + listItem.author)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }
    }

    private fun onClickCategoriesSelection() {
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(applicationContext, CategoriesActivity::class.java))
        }
    }

}