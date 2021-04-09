package com.bogdan.motivation

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.bogdan.motivation.databinding.ActivityCategoriesBinding
import com.bogdan.motivation.databinding.ActivityHelloBinding
import java.util.ArrayList

class CategoriesActivity : AppCompatActivity() {

    private var binding: ActivityCategoriesBinding? = null

//    var bntGeneralChose: Button? = null
//    var bntFavoriteChose: Button? = null

    var dbHelper: DBHelper? = null

    var quotesIsEmptyList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        dbHelper = DBHelper(applicationContext)
        getQuoteFromDB()

//        bntGeneralChose = findViewById(R.id.btn_general_chose)
//        bntFavoriteChose = findViewById(R.id.btn_favorite_chose)

        binding?.btnGeneralChose?.setOnClickListener {
            startActivity(Intent(applicationContext, MotivationActivity::class.java))
        }

        binding?.btnFavoriteChose?.setOnClickListener {
            if (quotesIsEmptyList.size > 0) {
                startActivity(Intent(applicationContext, FavoriteMotivationActivity::class.java))
            }
            else{
                Toast.makeText(this, "You don't have any favourite yet. :(", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getQuoteFromDB(){
        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val cursor: Cursor = database.query(
            TABLE_ALL_QUOTES,
            null,
            null,
            null,
            null,
            null,
            null,
            null)

        if (cursor.moveToFirst()){
            val nameIndex = cursor.getColumnIndex(KEY_QUOTE)
            val addressIndex = cursor.getColumnIndex(KEY_AUTHOR)
            val favoriteIndex = cursor.getColumnIndex(KEY_FAVORITE)
            do {
                if (cursor.getString(favoriteIndex) == "1") {
                    val quoteElement =
                        Quote(cursor.getString(nameIndex), cursor.getString(addressIndex))
                    quotesIsEmptyList.add(quoteElement)
                }
            }while (cursor.moveToNext())
        }

        cursor.close()
    }


}