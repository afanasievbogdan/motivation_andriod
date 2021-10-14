package com.bogdan.motivation.ui.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.CommentsList
import com.bogdan.motivation.data.entities.local.FavQuotesList
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.databinding.FragmentAccountBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
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

        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is State.SuccessState<*> ->
                    when (it.data) {
                        is String -> {
                            binding.tvSubTitle.text = resources.getString(
                                R.string.account_name, it.data
                            )
                        }
                        is User -> {
                            val user = it.data
                            binding.tvMyInfo.setOnClickListener {
                                findNavController().navigate(
                                    AccountFragmentDirections.actionAccountFragmentToAboutMeFragment(
                                        binding.tvTitle.text.toString(), binding.tvMyInfo.text.toString(), user, null, null
                                    )
                                )
                            }
                        }
                        is CommentsList -> {
                            val commentsList = it.data
                            binding.tvMyComment.setOnClickListener {
                                findNavController().navigate(
                                    AccountFragmentDirections.actionAccountFragmentToAboutMeFragment(
                                        binding.tvTitle.text.toString(), binding.tvMyComment.text.toString(), null, commentsList, null
                                    )
                                )
                            }
                        }
                        is FavQuotesList -> {
                            val quotes = it.data
                            binding.tvMyLikes.setOnClickListener {
                                findNavController().navigate(
                                    AccountFragmentDirections.actionAccountFragmentToAboutMeFragment(
                                        binding.tvTitle.text.toString(), binding.tvMyLikes.text.toString(), null, null, quotes
                                    )
                                )
                            }
                        }
                    }
                else -> {
                }
            }
        })
    }
}