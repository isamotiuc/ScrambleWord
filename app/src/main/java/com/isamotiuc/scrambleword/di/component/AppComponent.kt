package com.isamotiuc.scrambleword.di.component

import com.isamotiuc.scrambleword.ScrambleApplication
import com.isamotiuc.scrambleword.di.modules.AppModule
import com.isamotiuc.scrambleword.di.modules.BuildersModule
import com.isamotiuc.scrambleword.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class,
        NetworkModule::class]
)
interface AppComponent {
    fun inject(app: ScrambleApplication)
}