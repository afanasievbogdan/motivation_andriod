package com.bogdan.motivation.ui.fragments.motivation

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.databinding.DialogGetitBinding
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.ui.fragments.motivation.adapter.OnClickListenerMotivation
import com.bogdan.motivation.ui.fragments.motivation.adapter.QuotesViewPagerAdapter

class MotivationFragment : Fragment(R.layout.fragment_motivation), OnClickListenerMotivation {

    private var _binding: FragmentMotivationBinding? = null
    private val binding get() = _binding!!

    private val quotesList = ArrayList<Quote>()
    private val quotesViewPagerAdapter = QuotesViewPagerAdapter()
    private val args: MotivationFragmentArgs by navArgs()
    private val motivationViewModel: MotivationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMotivationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isFavoriteOpen = "0"

        motivationViewModel.readFavoriteOpenFromPermissionDb()
        motivationViewModel.motivationLiveData.observe(
            viewLifecycleOwner,
            {
                if (it is MotivationViewState.FavoriteOpenFromPermissionDb)
                    isFavoriteOpen = it.permissionFavoriteOpen
            }
        )

        if (isFavoriteOpen == "0") {
            motivationViewModel.readAllQuotesFromQuotesDb()
            motivationViewModel.motivationLiveData.observe(
                viewLifecycleOwner,
                {
                    if (it is MotivationViewState.AllQuotesFromQuotesDb)
                        quotesList.addAll(it.allQuotes)
                }
            )
        } else {
            motivationViewModel.readFavouriteQuoteFromQuotesDb()
            motivationViewModel.motivationLiveData.observe(
                viewLifecycleOwner,
                {
                    if (it is MotivationViewState.FavouriteQuoteFromQuotesDb)
                        quotesList.addAll(it.favouriteQuote)
                }
            )
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
        var isPopupPassed = "0"

        motivationViewModel.readPopupFromPermissionsDb()
        motivationViewModel.motivationLiveData.observe(
            viewLifecycleOwner,
            {
                if (it is MotivationViewState.PopupFromPermissionsDb)
                    isPopupPassed = it.permissionPopup
            }
        )

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
                motivationViewModel.insetToPermissionsDb("1", "1", "0")
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
        motivationViewModel.insertFavoriteKeyToQuotesDb(favorite, quote)
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
