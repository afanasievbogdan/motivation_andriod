package com.bogdan.motivation.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.data.api.RetrofitConfiguration
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.helpers.StylesUtils

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    private val applicationViewModel: ApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        applicationViewModel.connectToDb(applicationContext)

        RetrofitConfiguration.configureQuotesApi()

        applicationViewModel.readStyleFromStylesDb()
        applicationViewModel.applicationLiveData.observe(
            this,
            {
                StylesUtils.onActivityCreateSetStyle(this, it)
            }
        )
    }
}