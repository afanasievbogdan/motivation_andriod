package com.bogdan.motivation

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.databinding.ActivityHelloBinding
import com.bogdan.motivation.databinding.ActivityThemePickerBinding
import kotlin.system.exitProcess

class ThemePickerActivity : AppCompatActivity() {

    var dbHelper: DBHelper? = null

    private var binding: ActivityThemePickerBinding? = null

//    var tvLettingGo: TextView? = null
//    var tvFaithSpirituality: TextView? = null
//    var tvHappiness: TextView? = null
//    var tvStressAnxiety: TextView? = null
//    var tvPhysicalHealth: TextView? = null
//    var tvAchievingGoals: TextView? = null
//    var tvSelfEsteem: TextView? = null
//    var tvRelationships: TextView? = null

//    var tvExplanationTheme: TextView? = null
//    var grip: TableLayout? = null
//    var btnContinue2: Button? = null

    var tvExplanationThemeAnimation: Animation? = null
    var gripAnimation: Animation? = null
    var btnContinue2Animation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemePickerBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        dbHelper = DBHelper(applicationContext)

        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_ID, 1)
        contentValues.put(KEY_SETTING_COMPLETE, "1")
        contentValues.put(KEY_POPUP_COMPLETE, "0")

        database.insertWithOnConflict(
            TABLE_PERMISSIONS,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_REPLACE
        )

//        tvLettingGo = findViewById(R.id.tv_letting_go)
//        tvFaithSpirituality = findViewById(R.id.tv_faith_spirituality)
//        tvHappiness = findViewById(R.id.tv_happiness)
//        tvStressAnxiety = findViewById(R.id.tv_stress_anxiety)
//        tvPhysicalHealth = findViewById(R.id.tv_physical_health)
//        tvAchievingGoals = findViewById(R.id.tv_achieving_goals)
//        tvSelfEsteem = findViewById(R.id.tv_self_esteem)
//        tvRelationships = findViewById(R.id.tv_relationships)
//
//        tvExplanationTheme = findViewById(R.id.tvThemeExplanations)
//        grip = findViewById(R.id.gripLayout)
//        btnContinue2 = findViewById(R.id.btnContinue2)

        tvExplanationThemeAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        gripAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        btnContinue2Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)

        tvExplanationThemeAnimation?.startOffset = 750
        gripAnimation?.startOffset = 1250
        btnContinue2Animation?.startOffset = 1750

//        tvExplanationTheme?.startAnimation(tvExplanationThemeAnimation)
//        grip?.startAnimation(gripAnimation)
//        btnContinue2?.startAnimation(btnContinue2Animation)


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
    fun themeSettingsOnClick(view: View){

        if (view == binding?.tvLettingGo && !tvLettingGoPressed) {
            tvLettingGoPressed = true
            binding?.tvLettingGo?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvLettingGo?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvLettingGo && tvLettingGoPressed){
            tvLettingGoPressed = false
            binding?.tvLettingGo?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvLettingGo?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvFaithSpirituality && !tvFaithSpiritualityPressed) {
            tvFaithSpiritualityPressed = true
            binding?.tvFaithSpirituality?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvFaithSpirituality?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvFaithSpirituality && tvFaithSpiritualityPressed){
            tvFaithSpiritualityPressed = false
            binding?.tvFaithSpirituality?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvFaithSpirituality?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvHappiness && !tvHappinessPressed) {
            tvHappinessPressed = true
            binding?.tvHappiness?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvHappiness?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvHappiness && tvHappinessPressed){
            tvHappinessPressed = false
            binding?.tvHappiness?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvHappiness?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvStressAnxiety && !tvStressAnxietyPressed) {
            tvStressAnxietyPressed = true
            binding?.tvStressAnxiety?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvStressAnxiety?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvStressAnxiety && tvStressAnxietyPressed){
            tvStressAnxietyPressed = false
            binding?.tvStressAnxiety?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvStressAnxiety?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvPhysicalHealth && !tvPhysicalHealthPressed) {
            tvPhysicalHealthPressed = true
            binding?.tvPhysicalHealth?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvPhysicalHealth?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvPhysicalHealth && tvPhysicalHealthPressed){
            tvPhysicalHealthPressed = false
            binding?.tvPhysicalHealth?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvPhysicalHealth?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvAchievingGoals && !tvAchievingGoalsPressed) {
            tvAchievingGoalsPressed = true
            binding?.tvAchievingGoals?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvAchievingGoals?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvAchievingGoals && tvAchievingGoalsPressed){
            tvAchievingGoalsPressed = false
            binding?.tvAchievingGoals?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvAchievingGoals?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvSelfEsteem && !tvSelfEsteemPressed) {
            tvSelfEsteemPressed = true
            binding?.tvSelfEsteem?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvSelfEsteem?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvSelfEsteem && tvSelfEsteemPressed){
            tvSelfEsteemPressed = false
            binding?.tvSelfEsteem?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvSelfEsteem?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == binding?.tvRelationships && !tvRelationshipsPressed) {
            tvRelationshipsPressed = true
            binding?.tvRelationships?.setBackgroundResource(R.drawable.theme_btn_pressed)
            binding?.tvRelationships?.setTextColor(resources.getColor(R.color.white))
        } else if (view == binding?.tvRelationships && tvRelationshipsPressed){
            tvRelationshipsPressed = false
            binding?.tvRelationships?.setBackgroundResource(R.drawable.theme_btn)
            binding?.tvRelationships?.setTextColor(resources.getColor(R.color.black_grey))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

    fun onBntContinue2Clicked(view: View){
        if (tvLettingGoPressed || tvFaithSpiritualityPressed || tvHappinessPressed
            || tvStressAnxietyPressed || tvPhysicalHealthPressed || tvAchievingGoalsPressed
            || tvSelfEsteemPressed || tvRelationshipsPressed){
            dbHelper = DBHelper(applicationContext)
            val database: SQLiteDatabase = dbHelper!!.writableDatabase

            val contentValues = ContentValues()

            contentValues.put(KEY_ID, 1)
            if (!tvLettingGoPressed) {
                contentValues.put(KEY_LETTING_GO, 1)
            }else contentValues.put(KEY_LETTING_GO, 0)
            if (!tvFaithSpiritualityPressed) {
                contentValues.put(KEY_FAITH_SPIRITUALITY, 1)
            }else contentValues.put(KEY_FAITH_SPIRITUALITY, 0)
            if (!tvHappinessPressed) {
                contentValues.put(KEY_HAPPINESS, 1)
            }else contentValues.put(KEY_HAPPINESS, 0)
            if (!tvStressAnxietyPressed) {
                contentValues.put(KEY_STRESS_ANXIETY, 1)
            }else contentValues.put(KEY_STRESS_ANXIETY, 0)
            if (!tvPhysicalHealthPressed) {
                contentValues.put(KEY_PHYSICAL_HEALTH, 1)
            }else contentValues.put(KEY_PHYSICAL_HEALTH, 0)
            if (!tvAchievingGoalsPressed) {
                contentValues.put(KEY_ACHIEVING_GOALS, 1)
            }else contentValues.put(KEY_ACHIEVING_GOALS, 0)
            if (!tvSelfEsteemPressed) {
                contentValues.put(KEY_SELF_ESTEEM, 1)
            }else contentValues.put(KEY_SELF_ESTEEM, 0)
            if (!tvRelationshipsPressed) {
                contentValues.put(KEY_RELATIONSHIP, 1)
            }else contentValues.put(KEY_RELATIONSHIP, 0)

            database.insertWithOnConflict(
                TABLE_THEMES,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_IGNORE
            )

            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }else Toast.makeText(applicationContext, "Choose at least one category", Toast.LENGTH_SHORT).show()

    }

}