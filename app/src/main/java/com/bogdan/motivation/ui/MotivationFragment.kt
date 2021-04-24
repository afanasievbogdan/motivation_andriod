package com.bogdan.motivation.ui

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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.databinding.DialogGetitBinding
import com.bogdan.motivation.interfaces.OnClickListener
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote
import com.bogdan.motivation.recycleradapter.QuotesViewPagerAdapter
import java.util.*

class MotivationFragment : Fragment(R.layout.fragment_motivation), OnClickListener {

    private var _binding: FragmentMotivationBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager
    private val quotesList = ArrayList<Quote>()
    private val quotesViewPagerAdapter = QuotesViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentMotivationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (dbManager.readFavoriteOpenFromPermissionDb() == "0") {
            quotesList.addAll(dbManager.readAllQuotesFromQuotesDb())
        } else {
            quotesList.addAll(dbManager.readFavouriteQuoteFromQuotesDb())
        }

        initializePopup()
        initializeViewPager()
        onClickCategoriesSelection()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initializePopup() {
        val isPopupPassed = dbManager.readPopupFromPermissionsDb()

        if (isPopupPassed == "0") {
            val dialogBinding = DialogGetitBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(false)
                show()
            }

            dialogBinding.btnGotIt.setOnClickListener {
                dbManager.insetToPermissionsDb("1", "1", "0")
                dialog.dismiss()
            }
        }
    }

    private fun onClickCategoriesSelection() {
        binding.btnGeneral.setOnClickListener {
            val action = MotivationFragmentDirections.actionMotivationFragmentToCategoriesFragment()
            findNavController().navigate(action)
        }
    }

    private fun initializeViewPager() {
        with(binding.viewPagerMotivation) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = quotesViewPagerAdapter
            quotesViewPagerAdapter.setData(quotesList)
            quotesViewPagerAdapter.onClickListener = this@MotivationFragment
        }
    }

    override fun onFavoriteClickListener(isFavorite: Boolean, quote: String) {
        val favorite = if (isFavorite) "1" else "0"
        dbManager.insertFavoriteKeyToQuotesDb(favorite, quote)
    }

    override fun onShareClickListener(quote: String, author: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$quote $author")
            type = "text/plain"
        }
        startActivity(sendIntent)
    }
}