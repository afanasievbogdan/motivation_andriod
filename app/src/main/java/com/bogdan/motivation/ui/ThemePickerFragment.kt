package com.bogdan.motivation.ui

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentThemePickerBinding
import com.bogdan.motivation.db.DBConstants
import com.bogdan.motivation.db.DBManager

class ThemePickerFragment : Fragment(R.layout.fragment_theme_picker) {

    private var _binding: FragmentThemePickerBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentThemePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiAnimations()
        themeSettingsOnClick()
        onBntContinueClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setUiAnimations() {
        val tvThemeExplanationsAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val categoriesContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val btnContinueAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )

        tvThemeExplanationsAnimation.startOffset = 750
        categoriesContainerAnimation.startOffset = 1250
        btnContinueAnimation.startOffset = 1750

        with(binding) {
            tvThemeExplanations.startAnimation(tvThemeExplanationsAnimation)
            categoriesContainer.startAnimation(categoriesContainerAnimation)
            btnContinue.startAnimation(btnContinueAnimation)
        }
    }

    // todo selector drawable
    private var tvLettingGoPressed = false
    private var tvFaithSpiritualityPressed = false
    private var tvHappinessPressed = false
    private var tvStressAnxietyPressed = false
    private var tvPhysicalHealthPressed = false
    private var tvAchievingGoalsPressed = false
    private var tvSelfEsteemPressed = false
    private var tvRelationshipsPressed = false

    // todo selector drawable
    private fun themeSettingsOnClick() {

//        binding.tvLettingGo.setOnClickListener {
//            if (tvLettingGoPressed) {
//                tvLettingGoPressed = false
//                binding.tvLettingGo.setBackgroundResource(R.drawable.btn_theme)
//                binding.tvLettingGo.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.mine_shaft
//                    )
//                )
//
//            } else {
//                tvLettingGoPressed = true
//                binding.tvLettingGo.setBackgroundResource(R.drawable.btn_pressed_theme)
//                binding.tvLettingGo.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.white
//                    )
//                )
//            }
//        }

        binding.tvLettingGo.setOnClickListener {
            if (tvLettingGoPressed) {
                tvLettingGoPressed = false
                binding.tvLettingGo.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvLettingGoPressed = true
                binding.tvLettingGo.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvFaithSpirituality.setOnClickListener {
            if (tvFaithSpiritualityPressed) {
                tvFaithSpiritualityPressed = false
                binding.tvFaithSpirituality.setBackgroundResource(R.drawable.btn_theme)
                binding.tvFaithSpirituality.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvFaithSpiritualityPressed = true
                binding.tvFaithSpirituality.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvFaithSpirituality.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvHappiness.setOnClickListener {
            if (tvHappinessPressed) {
                tvHappinessPressed = false
                binding.tvHappiness.setBackgroundResource(R.drawable.btn_theme)
                binding.tvHappiness.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvHappinessPressed = true
                binding.tvHappiness.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvHappiness.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvStressAnxiety.setOnClickListener {
            if (tvStressAnxietyPressed) {
                tvStressAnxietyPressed = false
                binding.tvStressAnxiety.setBackgroundResource(R.drawable.btn_theme)
                binding.tvStressAnxiety.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvStressAnxietyPressed = true
                binding.tvStressAnxiety.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvStressAnxiety.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvPhysicalHealth.setOnClickListener {
            if (tvPhysicalHealthPressed) {
                tvPhysicalHealthPressed = false
                binding.tvPhysicalHealth.setBackgroundResource(R.drawable.btn_theme)
                binding.tvPhysicalHealth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvPhysicalHealthPressed = true
                binding.tvPhysicalHealth.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvPhysicalHealth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvAchievingGoals.setOnClickListener {
            if (tvAchievingGoalsPressed) {
                tvAchievingGoalsPressed = false
                binding.tvAchievingGoals.setBackgroundResource(R.drawable.btn_theme)
                binding.tvAchievingGoals.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvAchievingGoalsPressed = true
                binding.tvAchievingGoals.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvAchievingGoals.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvSelfEsteem.setOnClickListener {
            if (tvSelfEsteemPressed) {
                tvSelfEsteemPressed = false
                binding.tvSelfEsteem.setBackgroundResource(R.drawable.btn_theme)
                binding.tvSelfEsteem.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvSelfEsteemPressed = true
                binding.tvSelfEsteem.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvSelfEsteem.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }

        binding.tvRelationships.setOnClickListener {
            if (tvRelationshipsPressed) {
                tvRelationshipsPressed = false
                binding.tvRelationships.setBackgroundResource(R.drawable.btn_theme)
                binding.tvRelationships.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.mine_shaft
                    )
                )
            } else {
                tvRelationshipsPressed = true
                binding.tvRelationships.setBackgroundResource(R.drawable.btn_pressed_theme)
                binding.tvRelationships.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }
    }

    private fun onBntContinueClicked() {
        binding.btnContinue.setOnClickListener {
            if (tvLettingGoPressed || tvFaithSpiritualityPressed || tvHappinessPressed ||
                tvStressAnxietyPressed || tvPhysicalHealthPressed || tvAchievingGoalsPressed ||
                tvSelfEsteemPressed || tvRelationshipsPressed
            ) {
                val contentValuesThemes = ContentValues()

                contentValuesThemes.put(DBConstants.KEY_ID, 1)
                if (!tvLettingGoPressed) {
                    contentValuesThemes.put(DBConstants.KEY_LETTING_GO, 1)
                } else contentValuesThemes.put(DBConstants.KEY_LETTING_GO, 0)
                if (!tvFaithSpiritualityPressed) {
                    contentValuesThemes.put(DBConstants.KEY_FAITH_SPIRITUALITY, 1)
                } else contentValuesThemes.put(DBConstants.KEY_FAITH_SPIRITUALITY, 0)
                if (!tvHappinessPressed) {
                    contentValuesThemes.put(DBConstants.KEY_HAPPINESS, 1)
                } else contentValuesThemes.put(DBConstants.KEY_HAPPINESS, 0)
                if (!tvStressAnxietyPressed) {
                    contentValuesThemes.put(DBConstants.KEY_STRESS_ANXIETY, 1)
                } else contentValuesThemes.put(DBConstants.KEY_STRESS_ANXIETY, 0)
                if (!tvPhysicalHealthPressed) {
                    contentValuesThemes.put(DBConstants.KEY_PHYSICAL_HEALTH, 1)
                } else contentValuesThemes.put(DBConstants.KEY_PHYSICAL_HEALTH, 0)
                if (!tvAchievingGoalsPressed) {
                    contentValuesThemes.put(DBConstants.KEY_ACHIEVING_GOALS, 1)
                } else contentValuesThemes.put(DBConstants.KEY_ACHIEVING_GOALS, 0)
                if (!tvSelfEsteemPressed) {
                    contentValuesThemes.put(DBConstants.KEY_SELF_ESTEEM, 1)
                } else contentValuesThemes.put(DBConstants.KEY_SELF_ESTEEM, 0)
                if (!tvRelationshipsPressed) {
                    contentValuesThemes.put(DBConstants.KEY_RELATIONSHIP, 1)
                } else contentValuesThemes.put(DBConstants.KEY_RELATIONSHIP, 0)

                dbManager.insetToPermissionsDb("1", "0", "0")

                val action = ThemePickerFragmentDirections.actionThemePickerFragmentToMainFragment()
                findNavController().navigate(action)
            } else Toast.makeText(
                context,
                "Choose at least one category",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
