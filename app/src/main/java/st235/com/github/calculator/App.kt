package st235.com.github.calculator

import android.app.Application
import st235.com.github.calculator.di.DiInjector

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        DiInjector.run(this)
    }
}