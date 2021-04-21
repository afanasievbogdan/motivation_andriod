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
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.R
import com.bogdan.motivation.`interface`.OnClickListener
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote
import com.bogdan.motivation.recycleradapter.QuotesViewPagerAdapter
import java.util.*

class MotivationFragment : Fragment(R.layout.fragment_motivation), OnClickListener {

    private var _binding: FragmentMotivationBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager
    private var quotesList = ArrayList<Quote>()
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
        quotesList =
            if (dbManager.readFavoriteOpenFromPermissionDb() == "0") {
                dbManager.readAllQuotesFromQuotesDb()
            } else {
                dbManager.readFavouriteQuoteFromQuotesDb()
            }

        initializePopup()
        onClickCategoriesSelection()

        with(binding.viewPager2) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = quotesViewPagerAdapter
            with(quotesViewPagerAdapter) {
                setData(quotesList)
                onClickListener = this@MotivationFragment
            }
        }
    }

    private fun initializePopup() {
        val isPopupPassed = dbManager.readPopupFromPermissionsDb()

        if (isPopupPassed == "0") {
            val dialog = Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_getit)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(false)
                show()
            }

            val btnGetIt: Button = dialog.findViewById(R.id.btn_got_it)
            btnGetIt.setOnClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}