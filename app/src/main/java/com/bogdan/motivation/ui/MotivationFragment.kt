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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.DialogGetitBinding
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote
import com.bogdan.motivation.interfaces.OnClickListenerMotivation
import com.bogdan.motivation.recycleradapters.QuotesViewPagerAdapter

class MotivationFragment : Fragment(R.layout.fragment_motivation), OnClickListenerMotivation {

    private var _binding: FragmentMotivationBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbManager: DBManager
    private val quotesList = ArrayList<Quote>()
    private val quotesViewPagerAdapter = QuotesViewPagerAdapter()

    private val args: MotivationFragmentArgs by navArgs()

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

        binding.btnCategories.text = args.btnCategoriesText
        initializePopup()
        initializeViewPager()
        onClickCategoriesSelection()
        onClickThemeEditor()
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
        binding.btnCategories.setOnClickListener {
            findNavController().navigate(
                MotivationFragmentDirections.actionMotivationFragmentToCategoriesFragment()
            )
        }
    }

    private fun onClickThemeEditor() {
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(
                MotivationFragmentDirections.actionMotivationFragmentToThemeEditorFragment(
                    args.btnCategoriesText
                )
            )
        }
    }

    private fun initializeViewPager() {
        with(binding.viewPagerMotivation) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = quotesViewPagerAdapter
            quotesViewPagerAdapter.setData(quotesList)
            quotesViewPagerAdapter.onClickListenerMotivation = this@MotivationFragment
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
