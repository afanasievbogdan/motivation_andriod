package com.bogdan.motivation.ui.fragments.you

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.databinding.FragmentYouBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.hideKeyboard
import com.bogdan.motivation.helpers.playAnimationWithOffset
import javax.inject.Inject

class YouFragment : Fragment(R.layout.fragment_you) {

    @Inject
    lateinit var viewModel: YouViewModel

    private var _binding: FragmentYouBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
        _binding = FragmentYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAnimations()
        initSpinner()
        onClickBtnGetStarted()
        disableKeyboard()
        onClickLogIn()
        onClickRegister()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun disableKeyboard() {
        binding.fragmentBackground.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun initSpinner() {

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
        binding.spinnerYourSex.adapter = spinnerArrayAdapter

        binding.spinnerYourSex.onItemSelectedListener =
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
    }

    private fun setAnimations() {
        with(binding) {
            tvAboutYou.playAnimationWithOffset(
                animResId = R.anim.anim_fade_slow,
                750
            )
            containerYourName.playAnimationWithOffset(
                animResId = R.anim.anim_fade_fast,
                1500
            )
            containerYourAge.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 1750)
            containerYourSex.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 2000)
            btnGetStarted.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 2250)
        }
    }

    private fun onClickLogIn() {
        with(binding) {
            btnLogIn.setOnClickListener {
                containerYourSex.visibility = View.GONE
                containerYourAge.visibility = View.GONE
                containerYourName.visibility = View.GONE
                containerYourLogin.visibility = View.VISIBLE
                containerYourPassword.visibility = View.VISIBLE

                btnRegister.visibility = View.VISIBLE
                btnLogIn.visibility = View.GONE
            }
        }
    }

    private fun onClickRegister() {
        with(binding) {
            btnRegister.setOnClickListener {
                containerYourSex.visibility = View.VISIBLE
                containerYourAge.visibility = View.VISIBLE
                containerYourName.visibility = View.VISIBLE
                containerYourLogin.visibility = View.GONE
                containerYourPassword.visibility = View.GONE

                btnLogIn.visibility = View.VISIBLE
                btnRegister.visibility = View.GONE
            }
        }
    }

    private fun onClickBtnGetStarted() {
        binding.btnGetStarted.setOnClickListener {
            when {
                binding.tiYourName.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Не стесняйся, назови свое имя",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.tiYourAge.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Выбери свой возраст, это важно",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.spinnerYourSex.selectedItemPosition < 1 -> {
                    Toast.makeText(
                        requireContext(),
                        "Выбери, пожалуйста, свой пол",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    viewModel.insertUser(
                        User(
                            name = binding.tiYourName.text.toString(),
                            age = binding.tiYourAge.text.toString().toInt(),
                            sex = binding.spinnerYourSex.selectedItem.toString()
                        )
                    )

                    findNavController().navigate(
                        YouFragmentDirections.actionYouFragmentToNotificationSettingsFragment()
                    )
                }
            }
        }
    }
}