package com.bogdan.motivation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.databinding.ActivityCategoriesBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote
import java.util.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private val dbManager = DBManager(applicationContext)

    private var quotesIsEmptyList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbManager.openDb()
        dbManager.readFavouriteQuoteFromQuotesDb()

        binding.btnGeneralChose.setOnClickListener {
            startActivity(Intent(applicationContext, MotivationActivity::class.java))
        }

        binding.btnFavoriteChose.setOnClickListener {
            if (quotesIsEmptyList.size > 0) {
                startActivity(Intent(applicationContext, FavoriteMotivationActivity::class.java))
            } else {
                Toast.makeText(this, "You don't have any favourite yet. :(", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}