package com.bogdan.motivation.ui.fragments.themepicker

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
import com.bogdan.motivation.helpers.playAnimationWithOffset
import com.bogdan.motivation.repositories.RepositoryProvider
import com.bogdan.motivation.ui.fragments.themepicker.adapter.OnClickListenerThemes
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker), OnClickListenerThemes {

    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DBManager

    private val themesRecyclerViewAdapter = ThemesRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = RepositoryProvider.dbRepository.getDbInstance()
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

    override fun onThemeClickListener(theme: String) {
        db.insetToThemesDb(theme)
    }

    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            val isThemeChosen = db.readFromThemesDb() == "1"
            if (isThemeChosen) {
                db.insetToPermissionsDb("1", "0", "0")
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
