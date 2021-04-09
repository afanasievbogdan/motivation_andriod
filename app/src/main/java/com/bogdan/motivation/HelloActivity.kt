package com.bogdan.motivation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.databinding.ActivityHelloBinding
import kotlin.system.exitProcess

class HelloActivity : AppCompatActivity() {

    private var binding: ActivityHelloBinding? = null

//    var imgView : ImageView? = null
//    var tvLabel1: TextView? = null
//    var tvLabel2: TextView? = null
//    var tvLabel3: TextView? = null
//    var btnGetStarted: Button? =null

    var imageAnimation: Animation? = null
    var tv1Animation: Animation? = null
    var tv2Animation: Animation? = null
    var tv3Animation: Animation? = null
    var btnAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

//        imgView = findViewById(R.id.imgHelloView)
//        tvLabel1 = findViewById(R.id.tv1)
//        tvLabel2 = findViewById(R.id.tv2)
//        tvLabel3 = findViewById(R.id.tv3)
//        btnGetStarted = findViewById(R.id.btnStart)

        //TODO подобные операции выноси в функции и называй так, чтобы было понятно по названию что она делает
        imageAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        tv1Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        tv2Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        tv3Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        btnAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)

        tv1Animation?.startOffset = 750
        tv2Animation?.startOffset = 1500
        tv3Animation?.startOffset = 2250
        btnAnimation?.startOffset = 3000

        binding?.imgHelloView?.startAnimation(imageAnimation)
        binding?.tv1?.startAnimation(tv1Animation)
        binding?.tv2?.startAnimation(tv2Animation)
        binding?.tv3?.startAnimation(tv3Animation)
        binding?.btnStart?.startAnimation(btnAnimation)


    }

    fun onBntStartClicked(view: View){
        startActivity(Intent(applicationContext, NotificationSettingsActivity::class.java))
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

}