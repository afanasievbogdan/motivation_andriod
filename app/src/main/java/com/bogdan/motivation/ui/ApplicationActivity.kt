package com.bogdan.motivation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.db.DBManager

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbManager = DBManager(applicationContext)
        dbManager.openDb()


    }
}