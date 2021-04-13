package com.bogdan.motivation.activities

import android.app.Dialog
import android.content.Intent
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
import com.bogdan.motivation.recycleradapter.QuotesViewPagerAdapter
import com.bogdan.motivation.entities.Quote
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityMotivationBinding
import com.bogdan.motivation.db.DBManager
import java.util.*
import kotlin.system.exitProcess

class MotivationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotivationBinding
    private val dbManager = DBManager(applicationContext)
    private var quotesList = ArrayList<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotivationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbManager.openDb()
        quotesList = dbManager.readAllQuotesFromQuotesDb()

        initializePopup()
        onClickCategoriesSelection()

        with(binding.viewPager2) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter =
                QuotesViewPagerAdapter(
                    quotesList
                )
        }
    }

    private fun initializePopup() {
        val isPopupPassed = dbManager.readPopupFromPermissionsDb()

        if (isPopupPassed == "0") {
            val dialog = Dialog(this).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_getit)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(false)
                show()
            }

            val btnGetIt: Button = dialog.findViewById(R.id.btn_got_it)
            btnGetIt.setOnClickListener {
                dbManager.insetToPermissionsDb("1", "1")
                dialog.dismiss()
            }
        }
    }

    //TODO про адаптер и вх уже писали
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
                .inflate(R.layout.activity_motivation_single, parent, false)
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

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val listItem = quotesValue[holder.adapterPosition]
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
    }

    private fun onClickCategoriesSelection() {
        binding.btnGeneral.setOnClickListener {
            startActivity(Intent(applicationContext, CategoriesActivity::class.java))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

}
