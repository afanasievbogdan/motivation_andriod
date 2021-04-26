package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentHelloBinding

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

    private fun setUiAnimations() {
        val ivHeaderAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )
        val tvSelfCareAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )
        val tvSelfLoveAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )
        val tvSelfGrowthAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )
        val btnGetStartedAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )

        tvSelfCareAnimation.startOffset = 750
        tvSelfLoveAnimation.startOffset = 1500
        tvSelfGrowthAnimation.startOffset = 2250
        btnGetStartedAnimation.startOffset = 3000

        with(binding) {
            ivHeader.startAnimation(ivHeaderAnimation)
            tvSelfCare.startAnimation(tvSelfCareAnimation)
            tvSelfLove.startAnimation(tvSelfLoveAnimation)
            tvSelfGrowth.startAnimation(tvSelfGrowthAnimation)
            btnGetStarted.startAnimation(btnGetStartedAnimation)
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
