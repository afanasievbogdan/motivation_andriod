package com.bogdan.motivation.ui.fragments.styleeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.databinding.FragmentStyleEditorBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.di.modules.viewModule.ViewModelFactory
import com.bogdan.motivation.helpers.Styles
import com.bogdan.motivation.helpers.StylesUtils
import javax.inject.Inject

class StyleEditorFragment : Fragment(R.layout.fragment_style_editor) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var styleEditorViewModel: StyleEditorViewModel
    private var _binding: FragmentStyleEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        styleEditorViewModel = ViewModelProvider(this, viewModelFactory).get(StyleEditorViewModel::class.java)
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