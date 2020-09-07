package st235.com.github.calculator.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import st235.com.github.calculator.presentation.calculator.CalculatorViewModel
import st235.com.github.calculator.presentation.splash.SplashScreenViewModel
import kotlin.reflect.KClass


@MapKey
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CalculatorViewModel::class)
    abstract fun bindsCalculatorViewModel(calculatorViewModel: CalculatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    abstract fun bindsSplashScreenViewModel(splashScreenViewModel: SplashScreenViewModel): ViewModel

}