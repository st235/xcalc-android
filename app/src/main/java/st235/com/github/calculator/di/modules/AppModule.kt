package st235.com.github.calculator.di.modules

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import st235.com.github.uicore.themes.ThemeExtractor
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideResources(): Resources {
        return context.resources
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideThemeExtractor(resources: Resources): ThemeExtractor {
        return ThemeExtractor(resources)
    }
}