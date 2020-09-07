package st235.com.github.calculator.data

import io.reactivex.Observable
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.presentation.calculator.keyboard.action.ActionButton
import st235.com.github.calculator.presentation.calculator.keyboard.token.TokenButton
import st235.com.github.calculator_core.utils.TokenHelper
import st235.com.github.uicore.themes.ThemeNode
import javax.inject.Inject

class KeyboardManager @Inject constructor(
    private val tokenService: TokenService,
    private val themesManager: ThemesManager,
    private val widgetManager: WidgetsManager
) {

    fun observeKeyboard(): Observable<List<KeyboardButton>> {
        return widgetManager.observeActiveWidgets()
            .map {
                val buttons = mutableListOf<KeyboardButton>()
                buttons.addAll(it)
                buttons.addAll(defaultKeyboard())
                return@map buttons
            }
    }

    private fun defaultKeyboard(): List<KeyboardButton> {
        return listOf(
            createAction(id = ID_CLEAR, theme = themesManager.getActiveTheme().error, value = "C"),
            createToken(TokenHelper.ID_OPEN_PARENTHESIS),
            createToken(TokenHelper.ID_CLOSE_PARENTHESIS),
            createToken(TokenHelper.ID_OP_DIVIDE),

            createToken(TokenHelper.ID_NUM_1),
            createToken(TokenHelper.ID_NUM_2),
            createToken(TokenHelper.ID_NUM_3),
            createToken(TokenHelper.ID_OP_MULTIPLY),

            createToken(TokenHelper.ID_NUM_4),
            createToken(TokenHelper.ID_NUM_5),
            createToken(TokenHelper.ID_NUM_6),
            createToken(TokenHelper.ID_OP_PLUS),

            createToken(TokenHelper.ID_NUM_7),
            createToken(TokenHelper.ID_NUM_8),
            createToken(TokenHelper.ID_NUM_9),
            createToken(TokenHelper.ID_OP_MINUS),

            createToken(TokenHelper.ID_NUM_0, spanColumns = 2),
            createToken(TokenHelper.ID_DOT),
            createAction(id = ID_EQUALS, theme = themesManager.getActiveTheme().result, value = "=")
        )
    }

    private fun createAction(id: String, theme: ThemeNode, value: String): ActionButton {
        return ActionButton(
            id = id,
            themeNode = theme,
            value = value
        )
    }

    private fun createToken(id: String, spanColumns: Int = 1): TokenButton {
        val token = tokenService.findTokenById(id)
        val theme = if (TokenHelper.isOperator(token)) {
            themesManager.getActiveTheme().operators
        } else {
            themesManager.getActiveTheme().primary
        }

        return TokenButton(
            id = id,
            token = tokenService.findTokenById(id).shortValue,
            themeNode = theme,
            spanColumns = spanColumns
        )
    }

    companion object {
        val ID_CLEAR = "clear"
        val ID_EQUALS = "equals"
    }
}
