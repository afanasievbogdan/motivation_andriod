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

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    private val applicationViewModel: ApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        RetrofitConfiguration.configureQuotesApi()

        applicationViewModel.connectToDb(applicationContext)

        applicationViewModel.readCurrentStyle()
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