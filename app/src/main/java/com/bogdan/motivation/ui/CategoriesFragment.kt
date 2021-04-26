package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentCategoriesBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Quote

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager
    private val quotesList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quotesList.addAll(dbManager.readFavouriteQuoteFromQuotesDb())
        onClickCategories()
    }

    private fun onClickCategories() {
        binding.btnGeneralChose.setOnClickListener {
            dbManager.insetToPermissionsDb("1", "1", "0")
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToMotivationFragment(
                "General"
            )
            findNavController().navigate(action)
        }

        binding.btnFavoriteChose.setOnClickListener {
            dbManager.insetToPermissionsDb("1", "1", "1")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
