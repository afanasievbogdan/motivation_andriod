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
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.DialogGetitBinding
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.helpers.State
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

        binding.btnCategories.text = args.btnCategoriesText
        initializeObserver()
        initializeViewPager()
        onClickCategoriesSelection()
        onClickThemeEditor()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // TODO: 15.05.2021 обсервер в обсервере в обсервере..
    private fun initializeObserver() {
        motivationViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> ->
                    when (it.data) {
                        is Utils -> {
                            initializePopup(it.data)
                            getQuotes(it.data.isFavoriteTabOpen)
                        }
                        // TODO: model list<quote>
                        is List<*> -> quotesViewPagerAdapter.setData(it.data as List<Quote>)
                    }
                else -> {
                }
            }
        }
    }

    private fun getQuotes(isFavoriteTabOpen: Boolean) {
        if (isFavoriteTabOpen) {
            motivationViewModel.readFavouriteQuotesFromQuotesDb()
        } else {
            motivationViewModel.readAllQuotesFromQuotesDb()
        }
    }

    private fun initializePopup(utils: Utils) {

        if (!utils.isPopupPassed) {
            val dialogBinding = DialogGetitBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(false)
                show()
            }

            dialogBinding.btnGotIt.setOnClickListener {
                motivationViewModel.updatePermissions(
                    Utils(isSettingsPassed = true, isPopupPassed = true, isFavoriteTabOpen = false)
                )
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
            quotesViewPagerAdapter.onClickListenerMotivation = this@MotivationFragment
        }
    }

    override fun onFavoriteClickListener(quote: Quote) {
        motivationViewModel.updateQuote(quote.quote, quote.isFavorite)
    }

    override fun onShareClickListener(quote: Quote) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${quote.quote} ${quote.author}")
            type = "text/plain"
        }
        startActivity(sendIntent)
    }
}
