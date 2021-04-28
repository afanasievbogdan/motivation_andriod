package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bogdan.motivation.databinding.FragmentThemeEditorBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.ThemeUtils

class ThemeEditorFragment : Fragment() {

    private var _binding: FragmentThemeEditorBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbManager: DBManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentThemeEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBtnLightClicked()
        onBtnDarkClicked()
        onBtnBlueClicked()
        onBtnMixClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun onBtnLightClicked() {
        binding.btnLight.setOnClickListener {
            dbManager.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), ThemeUtils.LIGHT)
            )
        }
    }

    private fun onBtnDarkClicked() {
        binding.btnDark.setOnClickListener {
            dbManager.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), ThemeUtils.DARK)
            )
        }
    }

    private fun onBtnBlueClicked() {
        binding.btnBlue.setOnClickListener {
            dbManager.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), ThemeUtils.BLUE)
            )
        }
    }

    private fun onBtnMixClicked() {
        binding.btnMix.setOnClickListener {
            dbManager.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), ThemeUtils.MIX)
            )
        }
    }
}