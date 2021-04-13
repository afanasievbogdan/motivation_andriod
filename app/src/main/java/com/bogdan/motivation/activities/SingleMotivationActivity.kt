package com.bogdan.motivation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityMotivationSingleBinding
import com.bogdan.motivation.db.DBManager

class SingleMotivationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotivationSingleBinding
    private val dbManager = DBManager(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotivationSingleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbManager.openDb()
    }

    //TODO Как этим методом пользоваться в экземплярах активити в адаптере?
    fun trackingLikePressed(quote: String, author: String) {

        val isFavorite = dbManager.readFavoriteFromQuotesDb(quote)

        if (isFavorite == "0") {
            binding.imageLike.setImageResource(R.drawable.ic_like_unpressed)

        } else {
            binding.imageLike.setImageResource(R.drawable.ic_like_pressed)
        }

        binding.imageLike.setOnClickListener {

            val isFavoriteClicked = dbManager.readFavoriteFromQuotesDb(quote)

            if (isFavoriteClicked == "0") {
                binding.imageLike.setImageResource(R.drawable.ic_like_pressed)

                dbManager.insertFavoriteToQuotesDb("1", quote)
            } else {
                binding.imageLike.setImageResource(R.drawable.ic_like_unpressed)

                dbManager.insertFavoriteToQuotesDb("0", quote)
            }
        }

        binding.imageShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "$quote $author")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }
}