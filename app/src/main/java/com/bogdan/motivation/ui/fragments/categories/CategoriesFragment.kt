package com.bogdan.motivation.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.QuotesList
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentCategoriesBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
import javax.inject.Inject

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    @Inject
    lateinit var viewModel: CategoriesViewModel
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val quotesList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObserver()
        onClickCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is QuotesList -> quotesList.addAll(it.data.quotesList)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    private fun onClickCategories() {
        binding.btnGeneralChose.setOnClickListener {
            viewModel.updateUtils(
                Utils(
                    isSettingsPassed = true,
                    isPopupPassed = true,
                    isFavoriteTabOpen = false
                )
            )
            findNavController().navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToMotivationFragment(
                    "General"
                )
            )
        }

        binding.btnFavoriteChose.setOnClickListener {
            if (quotesList.isNotEmpty()) {
                viewModel.updateUtils(
                    Utils(
                        isSettingsPassed = true,
                        isPopupPassed = true,
                        isFavoriteTabOpen = true
                    )
                )
                findNavController().navigate(
                    CategoriesFragmentDirections.actionCategoriesFragmentToMotivationFragment(
                        "Favorite"
                    )
                )
            } else {
                Toast.makeText(context, "You don't have any favourite yet. :(", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}