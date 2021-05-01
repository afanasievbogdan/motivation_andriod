package com.bogdan.motivation.ui.fragments.style_editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bogdan.motivation.databinding.FragmentStyleEditorBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.ThemeUtils
import com.bogdan.motivation.helpers.ThemeUtils.Styles
import com.bogdan.motivation.repositories.RepositoryProvider

class StyleEditorFragment : Fragment() {
    // TODO add coroutines and view model
    private var _binding: FragmentStyleEditorBinding? = null
    private val binding get() = _binding!!

    // lateinit var styleEditorViewModel: StyleEditorViewModel
    private lateinit var db: DBManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // styleEditorViewModel = ViewModelProvider(this).get(StyleEditorViewModel::class.java)
        db = RepositoryProvider.dbRepository.dbManager
        _binding = FragmentStyleEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // onBtnLightClicked()
        onBtnDarkClicked()
        onBtnBlueClicked()
        onBtnMixClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

//    private fun onBtnLightClicked() {
//        binding.btnLight.setOnClickListener {
//            styleEditorViewModel.liveData.observe(
//                viewLifecycleOwner,
//                Observer {
//                    db.insertStyleToStylesDb(it)
//                }
//            )
//        }
//    }

    private fun onBtnDarkClicked() {
        binding.btnDark.setOnClickListener {
            db.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), Styles.DARK)
            )
        }
    }

    private fun onBtnBlueClicked() {
        binding.btnBlue.setOnClickListener {
            db.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), Styles.BLUE)
            )
        }
    }

    private fun onBtnMixClicked() {
        binding.btnMix.setOnClickListener {
            db.insertStyleToStylesDb(
                ThemeUtils.changeToTheme(requireActivity(), Styles.MIX)
            )
        }
    }
}