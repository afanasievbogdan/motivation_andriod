package com.bogdan.motivation.di.components

import com.bogdan.motivation.di.modules.*
import com.bogdan.motivation.ui.activity.MainActivity
import com.bogdan.motivation.ui.fragments.account.AccountFragment
import com.bogdan.motivation.ui.fragments.categories.CategoriesFragment
import com.bogdan.motivation.ui.fragments.hello.HelloFragment
import com.bogdan.motivation.ui.fragments.main.MainFragment
import com.bogdan.motivation.ui.fragments.motivation.MotivationFragment
import com.bogdan.motivation.ui.fragments.notificationsettings.NotificationSettingsFragment
import com.bogdan.motivation.ui.fragments.styleeditor.StyleEditorFragment
import com.bogdan.motivation.ui.fragments.themepicker.CategoriesPickerFragment
import com.bogdan.motivation.widget.AppWidget
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DBModule::class, AdapterModule::class, WorkerModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(helloFragment: HelloFragment)
    fun inject(notificationSettingsFragment: NotificationSettingsFragment)
    fun inject(categoriesPickerFragment: CategoriesPickerFragment)
    fun inject(motivationFragment: MotivationFragment)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(styleEditorFragment: StyleEditorFragment)
    fun inject(accountFragment: AccountFragment)
    fun inject(appWidget: AppWidget)
    fun workerFactoryComponent(): WorkerFactoryComponent.Builder
}