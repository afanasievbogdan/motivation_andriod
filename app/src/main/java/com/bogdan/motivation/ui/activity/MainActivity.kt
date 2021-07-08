package com.bogdan.motivation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.databinding.ActivityMainBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.Styles
import com.bogdan.motivation.helpers.StylesUtils
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Application.appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeObserver()
    }

    private fun initializeObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is Styles -> StylesUtils.onActivityCreateSetStyle(this, it.data)
                }
                is State.ErrorState -> Log.e("mainViewModel.state", it.errorMessage)
            }
        }
    }
}