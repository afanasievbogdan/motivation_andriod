package com.bogdan.motivation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.api.RetrofitConfiguration
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.helpers.StylesUtils

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    private val applicationViewModel = ApplicationViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        applicationViewModel.connectToDb(applicationContext)

        RetrofitConfiguration.configureQuotesApi()

        val style = applicationViewModel.readStyleFromStylesDb()
        StylesUtils.onActivityCreateSetStyle(this, style)
    }
}