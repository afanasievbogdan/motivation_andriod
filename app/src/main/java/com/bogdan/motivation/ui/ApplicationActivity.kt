package com.bogdan.motivation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.api.RetrofitConfiguration
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.ThemeUtils
import com.bogdan.motivation.repositories.RepositoryProvider

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding
    lateinit var db: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        RepositoryProvider.dbRepository.connectToDb(applicationContext)
        db = RepositoryProvider.dbRepository.dbManager

        RetrofitConfiguration.configureQuotesApi()

        val style = db.readStyleFromStylesDb()
        ThemeUtils.onActivityCreateSetTheme(this, style)
    }
}