package st235.com.github.calculator.data

import android.content.Context
import androidx.core.text.HtmlCompat
import io.reactivex.Observable
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.presentation.calculator.keyboard.action.ActionButton
import st235.com.github.calculator.presentation.calculator.keyboard.token.TokenButton
import st235.com.github.calculator.utils.isInLandscape
import st235.com.github.calculator_core.utils.TokenHelper
import st235.com.github.uicore.themes.ThemeNode
import javax.inject.Inject

class KeyboardManager @Inject constructor(
    private val context: Context,
    private val tokenService: TokenService,
    private val themesManager: ThemesManager,
    private val widgetManager: WidgetsManager
) {

    fun observeKeyboard(): Observable<List<KeyboardButton>> {
        return widgetManager.observeActiveWidgets()
            .map {
                val buttons = mutableListOf<KeyboardButton>()

                if (context.isInLandscape()) {
                    buttons.addAll(defaultLandscapeKeyboard())
                } else {
                    buttons.addAll(it)
                    buttons.addAll(defaultPortraitKeyboard())
                }

                return@map buttons
            }
    }

    private fun defaultPortraitKeyboard(): List<KeyboardButton> {
        /**
         * 4 columns x 4 rows
         */
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

    private fun defaultLandscapeKeyboard(): List<KeyboardButton> {
        /**
         * 10 columns x 3 rows
         */
        return listOf(
            createAction(id = ID_CLEAR, theme = themesManager.getActiveTheme().error, value = "C"),
            createToken(TokenHelper.ID_OPEN_PARENTHESIS),
            createToken(TokenHelper.ID_CLOSE_PARENTHESIS),
            createToken(TokenHelper.ID_FUN_SIN),
            createToken(TokenHelper.ID_FUN_COS),
            createToken(TokenHelper.ID_NUM_1),
            createToken(TokenHelper.ID_NUM_2),
            createToken(TokenHelper.ID_NUM_3),
            createToken(TokenHelper.ID_NUM_4),
            createToken(TokenHelper.ID_OP_MULTIPLY),
            createToken(TokenHelper.ID_OP_DIVIDE),

            createToken(TokenHelper.ID_OP_FACTORIAL),
            createToken(TokenHelper.ID_OP_POWER),
            createToken(TokenHelper.ID_FUN_SQRT),
            createToken(TokenHelper.ID_CONST_PI),
            createToken(TokenHelper.ID_NUM_5),
            createToken(TokenHelper.ID_NUM_6),
            createToken(TokenHelper.ID_NUM_7),
            createToken(TokenHelper.ID_NUM_8),
            createToken(TokenHelper.ID_OP_MINUS),
            createToken(TokenHelper.ID_OP_PLUS),

            createToken(TokenHelper.ID_FUN_LOG2),
            createToken(TokenHelper.ID_FUN_LN),
            createToken(TokenHelper.ID_FUN_TAN),
            createToken(TokenHelper.ID_OP_PERCENT),
            createToken(TokenHelper.ID_NUM_9),
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
            token = HtmlCompat.fromHtml(tokenService.findTokenById(id).shortValue, HtmlCompat.FROM_HTML_MODE_COMPACT),
            themeNode = theme,
            spanColumns = spanColumns
        )
    }

    companion object {
        val ID_CLEAR = "clear"
        val ID_EQUALS = "equals"
    }
}
