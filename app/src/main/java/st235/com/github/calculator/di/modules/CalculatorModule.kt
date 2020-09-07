package st235.com.github.calculator.di.modules

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import st235.com.github.calculator_core.TokenProcessorFactory
import javax.inject.Singleton

@Module
class CalculatorModule {

    @Provides
    @Singleton
    fun provideCoreTokenProcessorFactory(resources: Resources): TokenProcessorFactory {
        return TokenProcessorFactory(resources)
    }

}
