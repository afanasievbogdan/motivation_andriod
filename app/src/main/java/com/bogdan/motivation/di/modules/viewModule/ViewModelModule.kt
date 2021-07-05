package com.bogdan.motivation.di.modules.viewModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bogdan.motivation.helpers.ViewModelKey
import com.bogdan.motivation.ui.activity.MainActivityViewModel
import com.bogdan.motivation.ui.fragments.categories.CategoriesViewModel
import com.bogdan.motivation.ui.fragments.main.MainViewModel
import com.bogdan.motivation.ui.fragments.motivation.MotivationViewModel
import com.bogdan.motivation.ui.fragments.notificationsettings.NotificationSettingsViewModel
import com.bogdan.motivation.ui.fragments.styleeditor.StyleEditorViewModel
import com.bogdan.motivation.ui.fragments.themepicker.ThemePickerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
// todo: это точно нужно для инжекта вм?
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MotivationViewModel::class)
    abstract fun bindMotivationViewModel(motivationViewModel: MotivationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationSettingsViewModel::class)
    abstract fun bindNotificationSettingsViewModel(notificationSettingsViewModel: NotificationSettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StyleEditorViewModel::class)
    abstract fun bindStyleEditorViewModel(styleEditorViewModel: StyleEditorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ThemePickerViewModel::class)
    abstract fun bindThemePickerViewModel(themePickerViewModel: ThemePickerViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}