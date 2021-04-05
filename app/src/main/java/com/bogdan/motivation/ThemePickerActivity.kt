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
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class ThemePickerActivity : AppCompatActivity() {

    var dbHelper: DBHelper? = null

    var tvLettingGo: TextView? = null
    var tvFaithSpirituality: TextView? = null
    var tvHappiness: TextView? = null
    var tvStressAnxiety: TextView? = null
    var tvPhysicalHealth: TextView? = null
    var tvAchievingGoals: TextView? = null
    var tvSelfEsteem: TextView? = null
    var tvRelationships: TextView? = null

    var tvExplanationTheme: TextView? = null
    var grip: TableLayout? = null
    var btnContinue2: Button? = null

    var tvExplanationThemeAnimation: Animation? = null
    var gripAnimation: Animation? = null
    var btnContinue2Animation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_picker)

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

        tvLettingGo = findViewById(R.id.tv_letting_go)
        tvFaithSpirituality = findViewById(R.id.tv_faith_spirituality)
        tvHappiness = findViewById(R.id.tv_happiness)
        tvStressAnxiety = findViewById(R.id.tv_stress_anxiety)
        tvPhysicalHealth = findViewById(R.id.tv_physical_health)
        tvAchievingGoals = findViewById(R.id.tv_achieving_goals)
        tvSelfEsteem = findViewById(R.id.tv_self_esteem)
        tvRelationships = findViewById(R.id.tv_relationships)

        tvExplanationTheme = findViewById(R.id.tvThemeExplanations)
        grip = findViewById(R.id.gripLayout)
        btnContinue2 = findViewById(R.id.btnContinue2)

        tvExplanationThemeAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        gripAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        btnContinue2Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)

        tvExplanationThemeAnimation?.startOffset = 750
        gripAnimation?.startOffset = 1250
        btnContinue2Animation?.startOffset = 1750

        tvExplanationTheme?.startAnimation(tvExplanationThemeAnimation)
        grip?.startAnimation(gripAnimation)
        btnContinue2?.startAnimation(btnContinue2Animation)


    }

    private var tvLettingGoPressed = false
    private var tvFaithSpiritualityPressed = false
    private var tvHappinessPressed = false
    private var tvStressAnxietyPressed = false
    private var tvPhysicalHealthPressed = false
    private var tvAchievingGoalsPressed = false
    private var tvSelfEsteemPressed = false
    private var tvRelationshipsPressed = false
    fun themeSettingsOnClick(view: View){

        if (view == tvLettingGo && !tvLettingGoPressed) {
            tvLettingGoPressed = true
            tvLettingGo?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvLettingGo?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvLettingGo && tvLettingGoPressed){
            tvLettingGoPressed = false
            tvLettingGo?.setBackgroundResource(R.drawable.theme_btn)
            tvLettingGo?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvFaithSpirituality && !tvFaithSpiritualityPressed) {
            tvFaithSpiritualityPressed = true
            tvFaithSpirituality?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvFaithSpirituality?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvFaithSpirituality && tvFaithSpiritualityPressed){
            tvFaithSpiritualityPressed = false
            tvFaithSpirituality?.setBackgroundResource(R.drawable.theme_btn)
            tvFaithSpirituality?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvHappiness && !tvHappinessPressed) {
            tvHappinessPressed = true
            tvHappiness?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvHappiness?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvHappiness && tvHappinessPressed){
            tvHappinessPressed = false
            tvHappiness?.setBackgroundResource(R.drawable.theme_btn)
            tvHappiness?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvStressAnxiety && !tvStressAnxietyPressed) {
            tvStressAnxietyPressed = true
            tvStressAnxiety?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvStressAnxiety?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvStressAnxiety && tvStressAnxietyPressed){
            tvStressAnxietyPressed = false
            tvStressAnxiety?.setBackgroundResource(R.drawable.theme_btn)
            tvStressAnxiety?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvPhysicalHealth && !tvPhysicalHealthPressed) {
            tvPhysicalHealthPressed = true
            tvPhysicalHealth?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvPhysicalHealth?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvPhysicalHealth && tvPhysicalHealthPressed){
            tvPhysicalHealthPressed = false
            tvPhysicalHealth?.setBackgroundResource(R.drawable.theme_btn)
            tvPhysicalHealth?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvAchievingGoals && !tvAchievingGoalsPressed) {
            tvAchievingGoalsPressed = true
            tvAchievingGoals?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvAchievingGoals?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvAchievingGoals && tvAchievingGoalsPressed){
            tvAchievingGoalsPressed = false
            tvAchievingGoals?.setBackgroundResource(R.drawable.theme_btn)
            tvAchievingGoals?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvSelfEsteem && !tvSelfEsteemPressed) {
            tvSelfEsteemPressed = true
            tvSelfEsteem?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvSelfEsteem?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvSelfEsteem && tvSelfEsteemPressed){
            tvSelfEsteemPressed = false
            tvSelfEsteem?.setBackgroundResource(R.drawable.theme_btn)
            tvSelfEsteem?.setTextColor(resources.getColor(R.color.black_grey))
        }

        if (view == tvRelationships && !tvRelationshipsPressed) {
            tvRelationshipsPressed = true
            tvRelationships?.setBackgroundResource(R.drawable.theme_btn_pressed)
            tvRelationships?.setTextColor(resources.getColor(R.color.white))
        } else if (view == tvRelationships && tvRelationshipsPressed){
            tvRelationshipsPressed = false
            tvRelationships?.setBackgroundResource(R.drawable.theme_btn)
            tvRelationships?.setTextColor(resources.getColor(R.color.black_grey))
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
        startActivity(Intent(applicationContext, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

}