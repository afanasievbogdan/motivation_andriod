package com.bogdan.motivation.ui.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentAccountBinding
import com.bogdan.motivation.di.Application

class AccountFragment : Fragment(R.layout.fragment_account) {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickOk()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickOk() {
        binding.btnOk.setOnClickListener {
            findNavController().navigate(
                AccountFragmentDirections.actionAccountFragmentToMotivationFragment("General")
            )
        }
    }
}