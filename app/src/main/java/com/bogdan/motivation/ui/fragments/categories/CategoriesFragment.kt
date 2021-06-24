package com.bogdan.motivation.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentCategoriesBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.di.modules.viewModule.ViewModelFactory
import com.bogdan.motivation.helpers.State
import javax.inject.Inject

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var categoriesViewModel: CategoriesViewModel
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val quotesList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        categoriesViewModel = ViewModelProvider(this, viewModelFactory).get(CategoriesViewModel::class.java)
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

    @Suppress("UNCHECKED_CAST")
    private fun initializeObserver() {
        categoriesViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is List<*> -> quotesList.addAll(it.data as List<Quote>)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    private fun onClickCategories() {
        binding.btnGeneralChose.setOnClickListener {
            categoriesViewModel.updatePermissions(
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
            categoriesViewModel.updatePermissions(
                Utils(
                    isSettingsPassed = true,
                    isPopupPassed = true,
                    isFavoriteTabOpen = true
                )
            )
            if (quotesList.isNotEmpty()) {
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