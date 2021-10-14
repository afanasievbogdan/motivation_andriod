package com.bogdan.motivation.ui.fragments.aboutme

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.CommentsList
import com.bogdan.motivation.data.entities.local.FavQuotesList
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.databinding.DialogChangeProfileBinding
import com.bogdan.motivation.databinding.FragmentAboutMeBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.fragments.aboutme.adapter.AboutMeRecyclerViewAdapter
import javax.inject.Inject

class AboutMeFragment : Fragment(R.layout.fragment_about_me) {

    @Inject
    lateinit var viewModel: AboutMeViewModel

    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!

    private val aboutMeRecyclerViewAdapter = AboutMeRecyclerViewAdapter()
    private val args: AboutMeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        specifyNavArgs()
        onClickChangeProfile()
    }

    private fun specifyNavArgs() {
        when (args.desc) {
            "Информация обо мне" -> setUpUserInfoView()
            "Любимые цитаты" -> setUpFavoriteQuotesView()
            "Мои комментарии" -> setUpUserComments()
        }
    }

    private fun setUpUserInfoView() {
        setUpTitle()
        binding.tvChangeProfile.visibility = View.VISIBLE
        aboutMeRecyclerViewAdapter.setUserData(args.user!!)
    }

    private fun setUpFavoriteQuotesView() {
        setUpTitle()
        aboutMeRecyclerViewAdapter.setQuotesData(args.quotes ?: FavQuotesList(emptyList()))
    }

    private fun setUpUserComments() {
        setUpTitle()
        aboutMeRecyclerViewAdapter.setCommentsData(args.comments ?: CommentsList(emptyList()))
    }

    private fun setUpTitle() {
        with(binding) {
            tvTitle.text = args.title
            tvSubTitle.text = args.desc
        }
    }

    private fun onClickChangeProfile() {
        binding.tvChangeProfile.setOnClickListener {
            val dialogBinding = DialogChangeProfileBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(true)
                show()
            }
            with(dialogBinding) {
                viewModel.state.observe(viewLifecycleOwner) {
                    when (it) {
                        is State.SuccessState<*> ->
                            when (it.data) {
                                is User -> {
                                    tiYourName.setText(it.data.name)
                                    tiYourAge.setText(it.data.age.toString())
                                    tiYourLogin.setText(it.data.login ?: "")
                                    tiYourPassword.setText(it.data.password ?: "")
                                    aboutMeRecyclerViewAdapter.setUserData(it.data)
                                }
                            }
                        else -> {
                        }
                    }
                }

                val sexes = listOf("Выбрать пол", "Мужчина", "Женщина", "Другое")

                val spinnerArrayAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(
                    requireContext(), R.layout.item_spinner_dropdown, sexes
                ) {
                    override fun isEnabled(position: Int): Boolean {
                        return position != 0
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = super.getDropDownView(position, convertView, parent) as TextView
                        if (position == 0) {
                            view.setTextColor(Color.GRAY)
                        } else {
                            view.setTextColor(Color.BLACK)
                        }
                        return view
                    }
                }

                spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
                spinnerYourSex.adapter = spinnerArrayAdapter

                spinnerYourSex.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }

                btnSaveMe.setOnClickListener {
                    viewModel.insertUser(
                        User(
                            name = tiYourName.text.toString(),
                            age = tiYourAge.text.toString().toInt(),
                            sex = spinnerYourSex.selectedItem.toString(),
                            login = tiYourLogin.text.toString(),
                            password = tiYourPassword.text.toString()
                        )
                    )
                    viewModel.getUser()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerViewInfo.adapter = aboutMeRecyclerViewAdapter
    }
}