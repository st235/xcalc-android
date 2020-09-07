package st235.com.github.calculator.di

import android.content.Context
import st235.com.github.calculator.di.modules.AppModule
import st235.com.github.calculator.presentation.calculator.CalculatorActivity
import st235.com.github.calculator.presentation.splash.SplashScreenActivity

object DiInjector: AppComponent {

    private lateinit var appComponent: AppComponent

    fun run(context: Context) {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(context)).build()
    }

    override fun inject(activity: SplashScreenActivity) {
        appComponent.inject(activity)
    }

    override fun inject(activity: CalculatorActivity) {
        appComponent.inject(activity)
    }

}