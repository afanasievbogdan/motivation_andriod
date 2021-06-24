package com.bogdan.motivation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bogdan.motivation.databinding.ActivityMainBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.di.modules.viewModule.ViewModelFactory
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.Styles
import com.bogdan.motivation.helpers.StylesUtils
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Application.appComponent.inject(this)
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeObserver()
    }

    private fun initializeObserver() {
        mainActivityViewModel.state.observe(this) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is Styles -> StylesUtils.onActivityCreateSetStyle(this, it.data)
                    else -> StylesUtils.onActivityCreateSetStyle(this, Styles.DARK)
                }
                is State.ErrorState -> Log.e("mainViewModel.state", it.errorMessage)
            }
        }
    }
}