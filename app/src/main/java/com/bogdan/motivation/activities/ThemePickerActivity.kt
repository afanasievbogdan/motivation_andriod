package com.bogdan.motivation.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityThemePickerBinding
import com.bogdan.motivation.db.DBConstants
import com.bogdan.motivation.db.DBManager
import kotlin.system.exitProcess

class ThemePickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemePickerBinding
    private val dbManager = DBManager(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemePickerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbManager.openDb()

        setUiAnimations()
    }

    private fun setUiAnimations() {
        val tvExplanationThemeAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val gripAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val btnContinue2Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )

        tvExplanationThemeAnimation.startOffset = 750
        gripAnimation.startOffset = 1250
        btnContinue2Animation.startOffset = 1750

        binding.tvThemeExplanations.startAnimation(tvExplanationThemeAnimation)
        binding.gripLayout.startAnimation(gripAnimation)
        binding.btnContinue2.startAnimation(btnContinue2Animation)
    }

    //todo это плохо :)
    private var tvLettingGoPressed = false
    private var tvFaithSpiritualityPressed = false
    private var tvHappinessPressed = false
    private var tvStressAnxietyPressed = false
    private var tvPhysicalHealthPressed = false
    private var tvAchievingGoalsPressed = false
    private var tvSelfEsteemPressed = false
    private var tvRelationshipsPressed = false

    //selector drawable
    fun themeSettingsOnClick(view: View) {

        if (view == binding.tvLettingGo && !tvLettingGoPressed) {
            tvLettingGoPressed = true
            binding.tvLettingGo.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvLettingGo.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvLettingGo && tvLettingGoPressed) {
            tvLettingGoPressed = false
            binding.tvLettingGo.setBackgroundResource(R.drawable.btn_theme)
            binding.tvLettingGo.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvFaithSpirituality && !tvFaithSpiritualityPressed) {
            tvFaithSpiritualityPressed = true
            binding.tvFaithSpirituality.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvFaithSpirituality.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvFaithSpirituality && tvFaithSpiritualityPressed) {
            tvFaithSpiritualityPressed = false
            binding.tvFaithSpirituality.setBackgroundResource(R.drawable.btn_theme)
            binding.tvFaithSpirituality.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvHappiness && !tvHappinessPressed) {
            tvHappinessPressed = true
            binding.tvHappiness.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvHappiness.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvHappiness && tvHappinessPressed) {
            tvHappinessPressed = false
            binding.tvHappiness.setBackgroundResource(R.drawable.btn_theme)
            binding.tvHappiness.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvStressAnxiety && !tvStressAnxietyPressed) {
            tvStressAnxietyPressed = true
            binding.tvStressAnxiety.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvStressAnxiety.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvStressAnxiety && tvStressAnxietyPressed) {
            tvStressAnxietyPressed = false
            binding.tvStressAnxiety.setBackgroundResource(R.drawable.btn_theme)
            binding.tvStressAnxiety.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvPhysicalHealth && !tvPhysicalHealthPressed) {
            tvPhysicalHealthPressed = true
            binding.tvPhysicalHealth.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvPhysicalHealth.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvPhysicalHealth && tvPhysicalHealthPressed) {
            tvPhysicalHealthPressed = false
            binding.tvPhysicalHealth.setBackgroundResource(R.drawable.btn_theme)
            binding.tvPhysicalHealth.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvAchievingGoals && !tvAchievingGoalsPressed) {
            tvAchievingGoalsPressed = true
            binding.tvAchievingGoals.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvAchievingGoals.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvAchievingGoals && tvAchievingGoalsPressed) {
            tvAchievingGoalsPressed = false
            binding.tvAchievingGoals.setBackgroundResource(R.drawable.btn_theme)
            binding.tvAchievingGoals.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvSelfEsteem && !tvSelfEsteemPressed) {
            tvSelfEsteemPressed = true
            binding.tvSelfEsteem.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvSelfEsteem.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvSelfEsteem && tvSelfEsteemPressed) {
            tvSelfEsteemPressed = false
            binding.tvSelfEsteem.setBackgroundResource(R.drawable.btn_theme)
            binding.tvSelfEsteem.setTextColor(resources.getColor(R.color.mine_shaft))
        }

        if (view == binding.tvRelationships && !tvRelationshipsPressed) {
            tvRelationshipsPressed = true
            binding.tvRelationships.setBackgroundResource(R.drawable.btn_pressed_theme)
            binding.tvRelationships.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding.tvRelationships && tvRelationshipsPressed) {
            tvRelationshipsPressed = false
            binding.tvRelationships.setBackgroundResource(R.drawable.btn_theme)
            binding.tvRelationships.setTextColor(resources.getColor(R.color.mine_shaft))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

    fun onBntContinue2Clicked(view: View) {
        if (tvLettingGoPressed || tvFaithSpiritualityPressed || tvHappinessPressed
            || tvStressAnxietyPressed || tvPhysicalHealthPressed || tvAchievingGoalsPressed
            || tvSelfEsteemPressed || tvRelationshipsPressed
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

            dbManager.insetToPermissionsDb("1", "0")

            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(
                R.anim.slide_in,
                R.anim.slide_out
            )
        } else Toast.makeText(
            applicationContext,
            "Choose at least one category",
            Toast.LENGTH_SHORT
        ).show()
    }
}
