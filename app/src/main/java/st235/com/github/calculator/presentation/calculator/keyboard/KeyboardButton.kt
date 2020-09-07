package st235.com.github.calculator.presentation.calculator.keyboard

import androidx.annotation.ColorInt
import st235.com.github.uicore.themes.ThemeNode

abstract class KeyboardButton(
    private val themeNode: ThemeNode
) {
    abstract val id: String
    open val spanColumns: Int = 1

    @get:ColorInt val foregroundColor: Int
    get() = themeNode.foregroundColor

    @get:ColorInt val backgroundColor: Int
    get() = themeNode.backgroundColor

    @get:ColorInt val rippleColor: Int
    get() = themeNode.rippleColor
}
