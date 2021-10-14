package com.bogdan.motivation.ui.fragments.hello

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentHelloBinding
import com.bogdan.motivation.helpers.playAnimationWithOffset

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

        setAnimations()
        onClickBtnGetStarted()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setAnimations() {
        with(binding) {
            ivHeader.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 0)
            tvHere.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 750)
            tvSelfCare.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 2000)
            tvSelfLove.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 2750)
            tvSelfGrowth.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 3500)
            btnGetStarted.playAnimationWithOffset(animResId = R.anim.anim_fade_slow, 4250)
        }
    }

    private fun onClickBtnGetStarted() {
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(
                HelloFragmentDirections.actionHelloFragmentToYouFragment()
            )
        }
    }
}