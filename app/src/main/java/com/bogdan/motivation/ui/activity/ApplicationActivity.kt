package com.bogdan.motivation.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.data.api.RetrofitConfiguration
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.entities.Styles
import com.bogdan.motivation.databinding.ActivityApplicationBinding
import com.bogdan.motivation.helpers.StylesUtils
import com.bogdan.motivation.ui.State

// TODO: 15.05.2021 переименуй активити, например: MainActivity
class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    private val applicationViewModel: ApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // TODO: 15.05.2021 почему тут создаешь сервис? 
        RetrofitConfiguration.configureQuotesApi()
        // TODO: 15.05.2021 вынеси инит базы хотя бы во вм 
        applicationViewModel.connectToDb(applicationContext)
        // TODO: 15.05.2021 вызывай из вм 
        applicationViewModel.readCurrentStyle()
        // TODO: 15.05.2021 должно выглядеть как в конце файла коммент
        applicationViewModel.state.observe(
            this,
            {
                if (it is State.SuccessState<*> && it.data != null) {
                    StylesUtils.onActivityCreateSetStyle(
                        this,
                        it.data as Styles
                    )
                } else {
                    StylesUtils.onActivityCreateSetStyle(this, Styles.DARK)
                }
            }
        )
        // TODO: 15.05.2021 вызови из вм 
        // TODO: 15.05.2021 переименуй все permissions
        applicationViewModel.savePermissions(
            Permissions(
                1,
                isSettingsPassed = false,
                isPopupPassed = false,
                isFavoriteTabOpen = false
            )
        )
    }
}
//applicationViewModel.state.observe(this) {
//    when(it) {
//        is State.SuccessState<*> -> when(it.data) {
//            is Styles -> StylesUtils.onActivityCreateSetStyle(this, it.data)
//        }
//    }
//}