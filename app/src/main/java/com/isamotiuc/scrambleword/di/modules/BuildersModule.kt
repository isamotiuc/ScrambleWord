package com.isamotiuc.scrambleword.di.modules

import com.isamotiuc.scrambleword.main_screen.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}