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
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.databinding.DialogGetitBinding
import com.bogdan.motivation.databinding.FragmentMotivationBinding
import com.bogdan.motivation.ui.State
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
        onClickCategoriesSelection()
        onClickThemeEditor()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // TODO: 15.05.2021 обсервер в обсервере в обсервере..
    private fun initializeObserver() {
        motivationViewModel.state.observe(
            viewLifecycleOwner,
            {
                if (it != null && it is State.SuccessState<*>) {
                    when (it.data) {
                        is Permissions -> {
                            initializePopup(it.data)
                            if (it.data.isFavoriteTabOpen) {
                                motivationViewModel.favouriteQuotesLiveData.observe(
                                    viewLifecycleOwner,
                                    { favouriteQuotesList ->
                                        quotesList.addAll(favouriteQuotesList)
                                        initializeViewPager()
                                    }
                                )
                            } else {
                                motivationViewModel.allQuotesLiveData.observe(
                                    viewLifecycleOwner,
                                    { allQuotesList ->
                                        quotesList.addAll(allQuotesList)
                                        initializeViewPager()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    private fun initializePopup(permissions: Permissions) {

        if (!permissions.isPopupPassed) {
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
                    Permissions(1, isSettingsPassed = true, isPopupPassed = true, isFavoriteTabOpen = false)
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
            quotesViewPagerAdapter.setData(quotesList)
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
