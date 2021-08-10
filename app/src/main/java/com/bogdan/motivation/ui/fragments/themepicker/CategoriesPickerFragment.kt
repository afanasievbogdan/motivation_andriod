package com.bogdan.motivation.ui.fragments.themepicker

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Categories
import com.bogdan.motivation.data.entities.local.PickedCategoriesList
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentCategoriesPickerBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.playAnimationWithOffset
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter
import javax.inject.Inject
import com.bogdan.motivation.helpers.Categories as ThemesEnum

class CategoriesPickerFragment : Fragment(R.layout.fragment_categories_picker) {

    @Inject
    lateinit var viewModel: CategoriesPickerViewModel
    private var _binding: FragmentCategoriesPickerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var themesRecyclerViewAdapter: ThemesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentCategoriesPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeObserver()
        setAnimations()
        onBntContinueClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setAnimations() {
        with(binding) {
            tvThemeExplanations.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 750)
            recyclerViewThemes.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 1250)
            btnContinue.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 1750)
        }
    }

    private fun initializeObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is PickedCategoriesList -> themesRecyclerViewAdapter.setData(it.data.pickedCategoriesList)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewThemes.adapter = themesRecyclerViewAdapter
    }

    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            var counter = 0
            themesRecyclerViewAdapter.getData().forEach {
                if (it.isPicked) {
                    counter++
                }
            }
            val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = connectivityManager.activeNetworkInfo
            if (netWorkInfo != null && netWorkInfo.isConnectedOrConnecting) {
                if (counter > 0) {
                    viewModel.updateUtils(
                        Utils(
                            isSettingsPassed = true,
                            isPopupPassed = false,
                            isFavoriteTabOpen = false
                        )
                    )
                    themesRecyclerViewAdapter.getData().forEach {
                        if (it.isPicked) {
                            viewModel.insertTheme(Categories(0, ThemesEnum.valueOfThemeName(it.name)))
                        }
                    }
                    viewModel.insertTheme(Categories(0, ThemesEnum.motavation))
                    findNavController().navigate(CategoriesPickerFragmentDirections.actionThemePickerFragmentToMainFragment())
                } else {
                    Toast.makeText(
                        context,
                        "Choose at least one category",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    context,
                    "Check your Internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}