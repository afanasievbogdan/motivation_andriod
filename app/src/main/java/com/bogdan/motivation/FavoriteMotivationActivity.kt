package com.bogdan.motivation

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import java.util.ArrayList

class FavoriteMotivationActivity : AppCompatActivity() {

    private var btnFavorite: MaterialButton? = null
    private var btnEditFav: MaterialButton? = null
    private var btnAccFav: MaterialButton? = null

    var viewPager2Fav: ViewPager2? = null

    var dbHelper: DBHelper? = null

    var quotesFavList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_motivation)

        dbHelper = DBHelper(applicationContext)
        getQuoteFromDB()

        viewPager2Fav = findViewById(R.id.viewPager2_fav)
        viewPager2Fav?.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2Fav?.adapter = MyViewPager2AdapterFav(quotesFavList)

        btnFavorite = findViewById(R.id.btn_favorite)
        btnEditFav = findViewById(R.id.btn_edit_fav)
        btnAccFav = findViewById(R.id.btn_account_fav)

        btnFavorite?.alpha = 0.66F
        btnEditFav?.alpha = 0.66F
        btnAccFav?.alpha = 0.66F

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
                    quotesFavList.add(quoteElement)
                }
            }while (cursor.moveToNext())
        }

        cursor.close()
    }

    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textQuote: TextView = itemView.findViewById(R.id.textQuote)
        val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        var imageShare: ImageButton = itemView.findViewById(R.id.imageShare)
        var imageLike: ImageButton = itemView.findViewById(R.id.imageLike)
    }

    inner class MyViewPager2AdapterFav(private val quotesValue: List<Quote>) : RecyclerView.Adapter<MyViewHolderFav>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_single_motivation, parent, false)
            val myViewHolder = MyViewHolderFav(view)
            val position = myViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION){
                myViewHolder.textQuote.setOnClickListener{
                    Toast.makeText(parent.context, myViewHolder.adapterPosition, Toast.LENGTH_SHORT).show()
                }
            }
            return myViewHolder
        }

        override fun getItemCount(): Int {
            return quotesValue.count()
        }

        override fun onBindViewHolder(holderFav: MyViewHolderFav, position: Int) {
            val database: SQLiteDatabase = dbHelper!!.writableDatabase
            val contentValues = ContentValues()

            val listItem = quotesValue[holderFav.adapterPosition]
            holderFav.textQuote.text = listItem.quote
            holderFav.textAuthor.text = listItem.author

            val cursor: Cursor = database.rawQuery("SELECT $KEY_FAVORITE FROM $TABLE_ALL_QUOTES WHERE $KEY_QUOTE = '${listItem.quote}'", null)

            var isFavourite = "1"
            if (cursor.moveToFirst()){
                isFavourite = cursor.getString(0)
            }

            cursor.close()
            if (isFavourite == "0") {
                holderFav.imageLike.setImageResource(R.drawable.ic_like_unpressed)

            }else if (isFavourite == "1") {
                holderFav.imageLike.setImageResource(R.drawable.ic_like_pressed)
            }

            holderFav.imageLike.setOnClickListener {

                val cursor: Cursor = database.rawQuery("SELECT $KEY_FAVORITE FROM $TABLE_ALL_QUOTES WHERE $KEY_QUOTE = '${listItem.quote}'", null)

                var isFavourite = "1"
                if (cursor.moveToFirst()){
                    isFavourite = cursor.getString(0)
                }

                cursor.close()

                if (isFavourite == "0") {
                    holderFav.imageLike.setImageResource(R.drawable.ic_like_pressed)

                    contentValues.put(KEY_FAVORITE, "1")
                    database.update(TABLE_ALL_QUOTES, contentValues, "$KEY_QUOTE = ?",  arrayOf(listItem.quote))

                }
                else{
                    holderFav.imageLike.setImageResource(R.drawable.ic_like_unpressed)

                    contentValues.put(KEY_FAVORITE, "0")
                    database.update(TABLE_ALL_QUOTES, contentValues, "$KEY_QUOTE = ?",  arrayOf(listItem.quote))
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

    fun categoriesSelectionOnClicked(view: View) {
        startActivity(Intent(applicationContext, CategoriesActivity::class.java))
    }

}