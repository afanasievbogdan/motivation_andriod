package com.bogdan.motivation.ui.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentAccountBinding
import com.bogdan.motivation.di.Application
import javax.inject.Inject

class AccountFragment : Fragment(R.layout.fragment_account) {

    @Inject
    lateinit var viewModel: AccountViewModel
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}