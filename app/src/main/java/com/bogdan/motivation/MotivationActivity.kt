package com.bogdan.motivation

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.databinding.ActivityMotivationBinding
import com.bogdan.motivation.databinding.ActivityThemePickerBinding
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.system.exitProcess


class MotivationActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    private var binding: ActivityMotivationBinding? = null

//    var btnGeneral: MaterialButton? = null
//    var btnEdit: MaterialButton? = null
//    var btnAcc: MaterialButton? = null

//    var viewPager2: ViewPager2? = null

    var dbHelper: DBHelper? = null

    var quotesList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotivationBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        dbHelper = DBHelper(applicationContext)

        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val cursor: Cursor = database.rawQuery(
            "SELECT $KEY_POPUP_COMPLETE FROM $TABLE_PERMISSIONS WHERE $KEY_ID = 1", null
        )

        var isPermitted = "0"
        if (cursor.moveToFirst()) {
            isPermitted = cursor.getString(0)
        }

        cursor.close()

        if (isPermitted == "0") {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(R.layout.getit_dialog)
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
            dialog!!.show()

            val btnGetIt: Button = dialog!!.findViewById(R.id.btn_got_it)
            btnGetIt.setOnClickListener {
                val contentValues = ContentValues()

                contentValues.put(KEY_ID, 1)
                contentValues.put(KEY_SETTING_COMPLETE, "1")
                contentValues.put(KEY_POPUP_COMPLETE, "1")

                database.insertWithOnConflict(
                    TABLE_PERMISSIONS,
                    null,
                    contentValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
                dialog!!.dismiss()
            }
        }

        dbHelper = DBHelper(applicationContext)
        getQuoteFromDB()

//        viewPager2 = findViewById(R.id.viewPager2)
        binding?.viewPager2?.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding?.viewPager2?.adapter = MyViewPager2Adapter(quotesList)

//        btnGeneral = findViewById(R.id.btn_general)
//        btnEdit = findViewById(R.id.btn_edit)
//        btnAcc = findViewById(R.id.btn_account)

        binding?.btnGeneral?.alpha = 0.66F
        binding?.btnEdit?.alpha = 0.66F
        binding?.btnAccount?.alpha = 0.66F

    }

    private fun getQuoteFromDB() {
        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val cursor: Cursor = database.query(
            TABLE_ALL_QUOTES,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(KEY_QUOTE)
            val addressIndex = cursor.getColumnIndex(KEY_AUTHOR)
            do {
                val quoteElement =
                    Quote(cursor.getString(nameIndex), cursor.getString(addressIndex))
                quotesList.add(quoteElement)
            } while (cursor.moveToNext())
        }

        cursor.close()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textQuote: TextView = itemView.findViewById(R.id.textQuote)
        val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        var imageShare: ImageButton = itemView.findViewById(R.id.imageShare)
        var imageLike: ImageButton = itemView.findViewById(R.id.imageLike)
    }

    inner class MyViewPager2Adapter(private val quotesValue: List<Quote>) :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_single_motivation, parent, false)
            val myViewHolder = MyViewHolder(view)
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

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val database: SQLiteDatabase = dbHelper!!.writableDatabase
            val contentValues = ContentValues()

            val listItem = quotesValue[holder.adapterPosition]
            holder.textQuote.text = listItem.quote
            holder.textAuthor.text = listItem.author

            val cursor: Cursor = database.rawQuery(
                "SELECT $KEY_FAVORITE FROM $TABLE_ALL_QUOTES WHERE $KEY_QUOTE = '${listItem.quote}'",
                null
            )

            var isFavourite = "0"
            if (cursor.moveToFirst()) {
                isFavourite = cursor.getString(0)
            }

            cursor.close()
            if (isFavourite == "0") {
                holder.imageLike.setImageResource(R.drawable.ic_like_unpressed)

            } else if (isFavourite == "1") {
                holder.imageLike.setImageResource(R.drawable.ic_like_pressed)
            }

            holder.imageLike.setOnClickListener {

                val cursor: Cursor = database.rawQuery(
                    "SELECT $KEY_FAVORITE FROM $TABLE_ALL_QUOTES WHERE $KEY_QUOTE = '${listItem.quote}'",
                    null
                )

                var isFavourite = "0"
                if (cursor.moveToFirst()) {
                    isFavourite = cursor.getString(0)
                }

                cursor.close()

                if (isFavourite == "0") {
                    holder.imageLike.setImageResource(R.drawable.ic_like_pressed)

                    contentValues.put(KEY_FAVORITE, "1")
                    database.update(
                        TABLE_ALL_QUOTES,
                        contentValues,
                        "$KEY_QUOTE = ?",
                        arrayOf(listItem.quote)
                    )

                } else {
                    holder.imageLike.setImageResource(R.drawable.ic_like_unpressed)

                    contentValues.put(KEY_FAVORITE, "0")
                    database.update(
                        TABLE_ALL_QUOTES,
                        contentValues,
                        "$KEY_QUOTE = ?",
                        arrayOf(listItem.quote)
                    )
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

    }

    fun categoriesSelectionOnClicked(view: View) {
        startActivity(Intent(applicationContext, CategoriesActivity::class.java))
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

}
