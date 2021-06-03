package com.bogdan.motivation.ui.fragments.styleeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.databinding.FragmentStyleEditorBinding
import com.bogdan.motivation.helpers.Styles
import com.bogdan.motivation.helpers.StylesUtils

// TODO: 15.05.2021 добавь строку после конструктора
class StyleEditorFragment : Fragment() {

    private var _binding: FragmentStyleEditorBinding? = null
    private val binding get() = _binding!!

    private val styleEditorViewModel: StyleEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStyleEditorBinding.inflate(inflater, container, false)
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

    // TODO: 15.05.2021 если ты всегда сохраняешь в 1 ид, убери автогенерейт в модели и сделай дефолтное значение
    private fun onBtnLightClicked() {
        binding.btnLight.setOnClickListener {
            styleEditorViewModel.saveCurrentStyle(
                Style(style = StylesUtils.changeToStyle(requireActivity(), Styles.LIGHT))
            )
        }
    }

    private fun onBtnDarkClicked() {
        binding.btnDark.setOnClickListener {
            styleEditorViewModel.saveCurrentStyle(
                Style(style = StylesUtils.changeToStyle(requireActivity(), Styles.DARK))
            )
        }
    }

    private fun onBtnBlueClicked() {
        binding.btnBlue.setOnClickListener {
            styleEditorViewModel.saveCurrentStyle(
                Style(style = StylesUtils.changeToStyle(requireActivity(), Styles.BLUE))
            )
        }
    }

    private fun onBtnMixClicked() {
        binding.btnMix.setOnClickListener {
            styleEditorViewModel.saveCurrentStyle(
                Style(style = StylesUtils.changeToStyle(requireActivity(), Styles.MIX))
            )
        }
    }
}