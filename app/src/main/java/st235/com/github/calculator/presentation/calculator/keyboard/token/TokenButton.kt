package st235.com.github.calculator.presentation.calculator.keyboard.token

import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.uicore.themes.ThemeNode

data class TokenButton(
    override val id: String,
    override val spanColumns: Int,
    val token: String,
    val themeNode: ThemeNode
): KeyboardButton(themeNode)