package com.bogdan.motivation.ui.fragments.categories

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
import com.bogdan.motivation.repositories.RepositoryProvider

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DBManager
    private val quotesList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = RepositoryProvider.dbRepository.getDbInstance()
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quotesList.addAll(db.readFavouriteQuoteFromQuotesDb())
        onClickCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickCategories() {
        binding.btnGeneralChose.setOnClickListener {
            db.insetToPermissionsDb("1", "1", "0")
            findNavController().navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToMotivationFragment(
                    "General"
                )
            )
        }

        binding.btnFavoriteChose.setOnClickListener {
            db.insetToPermissionsDb("1", "1", "1")
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
