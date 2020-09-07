package st235.com.github.calculator.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import st235.com.github.calculator.di.DiInjector
import st235.com.github.calculator.presentation.base.BaseMvvmActivity
import st235.com.github.calculator.presentation.calculator.CalculatorActivity

class SplashScreenActivity: BaseMvvmActivity<SplashScreenViewModel>() {

    override fun onShouldInjectDependencies() {
        DiInjector.inject(this)
    }

    override fun onCreateViewModel(): SplashScreenViewModel {
        return provideViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeInitialized()
            .observe(this, Observer {
                val intent = Intent(this, CalculatorActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}
