package com.bogdan.motivation.di.components

import com.bogdan.motivation.di.modules.AdapterModule
import com.bogdan.motivation.di.modules.ApiModule
import com.bogdan.motivation.di.modules.AppModule
import com.bogdan.motivation.di.modules.DBModule
import com.bogdan.motivation.di.modules.viewModule.ViewModelModule
import com.bogdan.motivation.ui.activity.MainActivity
import com.bogdan.motivation.ui.fragments.categories.CategoriesFragment
import com.bogdan.motivation.ui.fragments.hello.HelloFragment
import com.bogdan.motivation.ui.fragments.main.MainFragment
import com.bogdan.motivation.ui.fragments.motivation.MotivationFragment
import com.bogdan.motivation.ui.fragments.notificationsettings.NotificationSettingsFragment
import com.bogdan.motivation.ui.fragments.styleeditor.StyleEditorFragment
import com.bogdan.motivation.ui.fragments.themepicker.ThemePickerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DBModule::class, ViewModelModule::class, AdapterModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(helloFragment: HelloFragment)
    fun inject(notificationSettingsFragment: NotificationSettingsFragment)
    fun inject(themePickerFragment: ThemePickerFragment)
    fun inject(motivationFragment: MotivationFragment)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(styleEditorFragment: StyleEditorFragment)
}