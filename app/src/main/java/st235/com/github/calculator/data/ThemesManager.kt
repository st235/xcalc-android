package st235.com.github.calculator.data

import st235.com.github.calculator.R
import st235.com.github.uicore.themes.Theme
import st235.com.github.uicore.themes.ThemeExtractor
import javax.inject.Inject

class ThemesManager @Inject constructor(
    private val themeExtractor: ThemeExtractor
) {

    private val themes: List<Theme> = themeExtractor.loadTheme(R.raw.themes)

    fun getActiveTheme(): Theme {
        return themes.find { it.default == true } ?: throw IllegalStateException()
    }
}
