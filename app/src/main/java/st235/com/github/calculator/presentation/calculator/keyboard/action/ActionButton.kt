package st235.com.github.calculator.presentation.calculator.keyboard.action

import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.uicore.themes.ThemeNode

data class ActionButton(
    override val id: String,
    val value: String,
    val themeNode: ThemeNode
): KeyboardButton(themeNode)
