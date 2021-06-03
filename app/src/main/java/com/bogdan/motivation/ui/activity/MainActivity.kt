package com.bogdan.motivation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.helpers.Styles
import com.bogdan.motivation.helpers.StylesUtils

// TODO: 15.05.2021 переименуй активити, например: MainActivity
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // TODO: 15.05.2021 почему тут создаешь сервис? 
        // RepositoryProvider.dbRepository.db = ApplicationDatabase.getDB(applicationContext)

        // TODO: 15.05.2021 вынеси инит базы хотя бы во вм
        // mainViewModel.connectToDb(applicationContext)
        // TODO: 15.05.2021 вызывай из вм 

        // TODO: 15.05.2021 должно выглядеть как в конце файла коммент

        mainActivityViewModel.state.observe(this) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is Styles -> StylesUtils.onActivityCreateSetStyle(this, it.data)
                    else -> StylesUtils.onActivityCreateSetStyle(this, Styles.DARK)
                }
                is State.ErrorState -> Log.e("mainViewModel.state", it.errorMessage)
            }
        }

        // TODO: 15.05.2021 вызови из вм 
        // TODO: 15.05.2021 переименуй все permissions
    }
}
