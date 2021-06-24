package com.bogdan.motivation.di.modules

import com.bogdan.motivation.ui.fragments.motivation.adapter.QuotesViewPagerAdapter
import com.bogdan.motivation.ui.fragments.themepicker.adapter.ThemesRecyclerViewAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun provideQuotesViewPagerAdapter(): QuotesViewPagerAdapter = QuotesViewPagerAdapter()

    @Provides
    fun provideThemesRecyclerViewAdapter(): ThemesRecyclerViewAdapter = ThemesRecyclerViewAdapter()
}