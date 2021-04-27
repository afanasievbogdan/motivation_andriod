package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentHelloBinding
import com.bogdan.motivation.extensions.playAnimation

class HelloFragment : Fragment(R.layout.fragment_hello) {

    private var _binding: FragmentHelloBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiAnimations()
        onClickBtnGetStarted()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // TODO: замени это на экстеншен для вьюхи, тут должно быть 5 строк + засэтить это для каждой вью ✓ DONE
    // TODO: для экстеншенов создай отдельный файл ✓ DONE
    private fun setUiAnimations() {
        with(binding) {
            ivHeader.playAnimation(animResId = R.anim.anim_fade_slow, 0)
            tvSelfCare.playAnimation(animResId = R.anim.anim_fade_slow, 750)
            tvSelfLove.playAnimation(animResId = R.anim.anim_fade_slow, 1500)
            tvSelfGrowth.playAnimation(animResId = R.anim.anim_fade_slow, 2250)
            btnGetStarted.playAnimation(animResId = R.anim.anim_fade_slow, 3000)
        }
    }

    private fun onClickBtnGetStarted() {
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(
                HelloFragmentDirections.actionHelloFragmentToNotificationSettingsFragment()
            )
        }
    }
}
