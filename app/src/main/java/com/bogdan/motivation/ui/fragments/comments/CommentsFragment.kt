package com.bogdan.motivation.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentCommentsBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.hideKeyboard
import com.bogdan.motivation.ui.fragments.comments.adapter.CommentsRecyclerViewAdapter
import javax.inject.Inject

class CommentsFragment : Fragment(R.layout.fragment_comments) {

    @Inject
    lateinit var viewModel: CommentsViewModel
    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private val commentsRecyclerViewAdapter = CommentsRecyclerViewAdapter()
    private val args: CommentsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObserver()
        initializeRecyclerView()
        onSendButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewComments.adapter = commentsRecyclerViewAdapter
        commentsRecyclerViewAdapter.setData(args.quote.comments)
    }

    private fun initializeObserver() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is State.SuccessState<*> ->
                    when (it.data) {
                        is String -> {
                            commentsRecyclerViewAdapter.setName(it.data)
                        }
                    }
                else -> {
                }
            }
        })
    }

    private fun onSendButtonClicked() {
        binding.btnSubmit.setOnClickListener {
            if (binding.tiComment.text.toString() == "") {
                Toast.makeText(requireContext(), "Сначала введите текст", Toast.LENGTH_SHORT).show()
            } else {
                args.quote.comments.add(binding.tiComment.text.toString())
                viewModel.updateQuoteComments(args.quote.quote, args.quote.comments)
                commentsRecyclerViewAdapter.setData(args.quote.comments)
                binding.tiComment.text.clear()
                hideKeyboard()
            }
        }
    }
}