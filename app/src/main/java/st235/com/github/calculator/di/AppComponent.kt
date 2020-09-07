package st235.com.github.calculator.di

import dagger.Component
import st235.com.github.calculator.di.modules.AppModule
import st235.com.github.calculator.di.modules.CalculatorModule
import st235.com.github.calculator.di.modules.ViewModelModule
import st235.com.github.calculator.presentation.calculator.CalculatorActivity
import st235.com.github.calculator.presentation.splash.SplashScreenActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, CalculatorModule::class])
interface AppComponent {

    fun inject(activity: SplashScreenActivity)
    fun inject(activity: CalculatorActivity)

}