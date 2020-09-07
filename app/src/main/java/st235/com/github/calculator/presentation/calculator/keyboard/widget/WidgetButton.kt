package st235.com.github.calculator.presentation.calculator.keyboard.widget

import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.uicore.themes.ThemeNode

class WidgetButton(
    override val id: String,
    val token: CharSequence,
    val themeNode: ThemeNode
): KeyboardButton(themeNode) {
    override val spanColumns: Int = 1
}
