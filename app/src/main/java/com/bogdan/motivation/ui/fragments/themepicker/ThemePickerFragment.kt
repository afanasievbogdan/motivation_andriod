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
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentThemePickerBinding
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.Themes
import com.bogdan.motivation.helpers.playAnimationWithOffset
import com.bogdan.motivation.ui.fragments.themepicker.adapter.OnClickListenerThemes
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter
import java.util.*
import kotlin.collections.ArrayList

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker), OnClickListenerThemes {

    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!

    private val themePickerViewModel: ThemePickerViewModel by viewModels()
    private val themesRecyclerViewAdapter = ThemesRecyclerViewAdapter()
    private val pickedThemes = ArrayList<Themes>()

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
        themePickerViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is List<*> -> themesRecyclerViewAdapter.setData(it.data as List<String>)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    // TODO: 15.05.2021 не вызывай функции репо из фрагмента
    private fun initializeRecyclerView() {
        with(binding.recyclerViewThemes) {
            adapter = themesRecyclerViewAdapter
            themesRecyclerViewAdapter.onClickListenerThemes = this@ThemePickerFragment
        }
    }

    // TODO: Dirty
    override fun onThemeClickListener(theme: String, picked: Boolean) {
        val themeInEnumStyle = theme
            .replaceFirst(" ", "_")
            .replace(" ", "")
            .replace("&", "")
            .replace("-", "_")
            .uppercase()
        val themeEnum = Themes.valueOf(themeInEnumStyle)

        if (picked) pickedThemes.add(themeEnum)
        else pickedThemes.remove(themeEnum)
    }

    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            if (pickedThemes.isNotEmpty()) {
                themePickerViewModel.updatePermissions(
                    Utils(
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
