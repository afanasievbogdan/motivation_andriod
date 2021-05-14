package com.bogdan.motivation.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.databinding.FragmentCategoriesBinding
import com.bogdan.motivation.ui.State

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private val quotesList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        categoriesViewModel.state.observe(
            viewLifecycleOwner,
            {
                if (it != null && it is State.SuccessState<*>) {
                    when (it.data) {
                        is List<*> -> quotesList.addAll(it.data as List<Quote>)
                    }
                }
            }
        )
    }

    private fun onClickCategories() {
        binding.btnGeneralChose.setOnClickListener {
            categoriesViewModel.updatePermissions(
                Permissions(
                    1,
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
                Permissions(
                    1,
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
