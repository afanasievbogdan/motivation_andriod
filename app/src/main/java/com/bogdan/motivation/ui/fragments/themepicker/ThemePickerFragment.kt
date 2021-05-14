package com.bogdan.motivation.ui.fragments.themepicker

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
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.databinding.FragmentThemePickerBinding
import com.bogdan.motivation.helpers.playAnimationWithOffset
import com.bogdan.motivation.ui.fragments.themepicker.adapter.OnClickListenerThemes
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker), OnClickListenerThemes {

    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!

    private val themePickerViewModel: ThemePickerViewModel by viewModels()
    private val themesRecyclerViewAdapter = ThemesRecyclerViewAdapter()
    private val pickedThemes = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
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

    private fun initializeRecyclerView() {
        with(binding.recyclerViewThemes) {
            adapter = themesRecyclerViewAdapter
            themesRecyclerViewAdapter.setData(
                RepositoryProvider.themesRepository.getThemeList()
            )
            themesRecyclerViewAdapter.onClickListenerThemes = this@ThemePickerFragment
        }
    }

    override fun onThemeClickListener(theme: String, picked: Boolean) {
        if (picked) pickedThemes.add(theme)
        else pickedThemes.remove(theme)
    }

    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            if (pickedThemes.isNotEmpty()) {
                themePickerViewModel.updatePermissions(
                    Permissions(
                        1,
                        isSettingsPassed = true,
                        isPopupPassed = false,
                        isFavoriteTabOpen = false
                    )
                )
                findNavController().navigate(ThemePickerFragmentDirections.actionThemePickerFragmentToMainFragment())
            } else {
                Toast.makeText(
                    context,
                    "Choose at least one category",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
