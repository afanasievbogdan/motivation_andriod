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

    private fun setUiAnimations() {
        val imageAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val tv1Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val tv2Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val tv3Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val btnAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )

        tv1Animation.startOffset = 750
        tv2Animation.startOffset = 1500
        tv3Animation.startOffset = 2250
        btnAnimation.startOffset = 3000

        binding.imgHello.startAnimation(imageAnimation)
        binding.tvSelfCare.startAnimation(tv1Animation)
        binding.tvSelfLove.startAnimation(tv2Animation)
        binding.tvSelfGrowth.startAnimation(tv3Animation)
        binding.btnGetStarted.startAnimation(btnAnimation)
    }

    private fun onClickBtnGetStarted() {
        binding.btnGetStarted.setOnClickListener {
            val action = HelloFragmentDirections.actionHelloFragmentToNotificationSettingsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}