package com.bogdan.motivation.ui.fragments.themepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Themes
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentThemePickerBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.di.modules.viewModule.ViewModelFactory
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.playAnimationWithOffset
import com.bogdan.motivation.ui.fragments.themepicker.adapter.OnClickListenerThemes
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import com.bogdan.motivation.helpers.Themes as ThemesEnum

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker), OnClickListenerThemes {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var themePickerViewModel: ThemePickerViewModel
    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!

    private val themesRecyclerViewAdapter = ThemesRecyclerViewAdapter()
    private val pickedThemes = ArrayList<ThemesEnum>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        themePickerViewModel = ViewModelProvider(this, viewModelFactory).get(ThemePickerViewModel::class.java)
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

    @Suppress("UNCHECKED_CAST")
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

    private fun initializeRecyclerView() {
        with(binding.recyclerViewThemes) {
            adapter = themesRecyclerViewAdapter
            themesRecyclerViewAdapter.onClickListenerThemes = this@ThemePickerFragment
        }
    }

    // TODO: Dirty
    override fun onThemeClickListener(theme: String, picked: Boolean) {
        val themeEnum = ThemesEnum.valueOfThemeName(theme)
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
                pickedThemes.forEach {
                    themePickerViewModel.insertTheme(Themes(0, it))
                }
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