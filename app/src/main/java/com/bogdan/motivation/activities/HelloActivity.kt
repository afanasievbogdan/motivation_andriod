package com.bogdan.motivation.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityHelloBinding
import kotlin.system.exitProcess

class HelloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onClickBntStart()
        setUiAnimations()

    }

    private fun setUiAnimations() {
        val imageAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val tv1Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val tv2Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val tv3Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val btnAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )

        tv1Animation.startOffset = 750
        tv2Animation.startOffset = 1500
        tv3Animation.startOffset = 2250
        btnAnimation.startOffset = 3000

        binding.imgHelloView.startAnimation(imageAnimation)
        binding.tv1.startAnimation(tv1Animation)
        binding.tv2.startAnimation(tv2Animation)
        binding.tv3.startAnimation(tv3Animation)
        binding.btnStart.startAnimation(btnAnimation)
    }

    private fun onClickBntStart() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(applicationContext, NotificationSettingsActivity::class.java))
            overridePendingTransition(
                R.anim.slide_in,
                R.anim.slide_out
            )
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

}