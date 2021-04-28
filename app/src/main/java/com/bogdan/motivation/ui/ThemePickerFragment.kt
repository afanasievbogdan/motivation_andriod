package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentThemePickerBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.playAnimation
import com.bogdan.motivation.interfaces.OnClickListenerThemes
import com.bogdan.motivation.recycleradapters.ThemesRecyclerViewAdapter

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker), OnClickListenerThemes {

    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbManager: DBManager

    // TODO: почему сразу не инициализировать лист при обьявлении переменной ✓ DONE
    private val themeList = listOf(
        "Letting go",
        "Happiness",
        "Physical Health",
        "Self-esteem",
        "Faith & Spirituality",
        "Stress & Anxiety",
        "Achieving goals",
        "Relationships"
    )

    private val themesRecyclerViewAdapter = ThemesRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentThemePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setUiAnimations()
        onBntContinueClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // TODO: замени это на экстеншен для вьюхи ✓ DONE
    private fun setUiAnimations() {
        with(binding) {
            tvThemeExplanations.playAnimation(animResId = R.anim.anim_fade_slow, 750)
            recyclerViewThemes.playAnimation(animResId = R.anim.anim_fade_slow, 1250)
            btnContinue.playAnimation(animResId = R.anim.anim_fade_slow, 1750)
        }
    }

    private fun initializeRecyclerView() {
        with(binding.recyclerViewThemes) {
            adapter = themesRecyclerViewAdapter
            themesRecyclerViewAdapter.setData(themeList)
            themesRecyclerViewAdapter.onClickListenerThemes = this@ThemePickerFragment
        }
    }

    override fun onThemeClickListener(theme: String) {
        dbManager.insetToThemesDb(theme)
    }

    // TODO: добаBь {} для else ✓ DONE
    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            val isThemeChosen = dbManager.readFromThemesDb() == "1"
            if (isThemeChosen) {
                dbManager.insetToPermissionsDb("1", "0", "0")
                val action = ThemePickerFragmentDirections.actionThemePickerFragmentToMainFragment()
                findNavController().navigate(action)
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
