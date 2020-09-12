package st235.com.github.calculator.presentation.calculator.input

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.text.HtmlCompat
import st235.com.github.calculator.data.ThemesManager
import st235.com.github.calculator.presentation.calculator.CalculatorScreenData
import st235.com.github.calculator_core.ProcessingResult
import st235.com.github.calculator_core.tokens.Token
import st235.com.github.calculator_core.utils.TokenHelper
import st235.com.github.uicore.spans.TokenSpan
import st235.com.github.uicore.themes.ThemeNode
import javax.inject.Inject

class InputProcessor @Inject constructor(
    private val themesManager: ThemesManager
) {

    private val autocompleteTheme: ThemeNode
    get() = themesManager.getActiveTheme().hints

    private val operatorsTheme: ThemeNode
    get() = themesManager.getActiveTheme().operators

    private val primariesTheme: ThemeNode
    get() = themesManager.getActiveTheme().primary

    fun process(result: ProcessingResult): CalculatorScreenData {
        return CalculatorScreenData(
            input = processInput(result),
            output = processOutput(result)
        )
    }

    fun processInput(result: ProcessingResult): CharSequence {
        val builder = SpannableStringBuilder()

        for (token in result.input) {
            if (TokenHelper.isOperator(token)) {
                builder.append(spannableFrom(operatorsTheme, token))
            } else {
                builder.append(spannableFrom(primariesTheme, token))
            }
        }

        builder.append(spannableFrom(autocompleteTheme, result.balanceSuffix))

        return builder
    }

    fun processOutput(result: ProcessingResult): CharSequence {
        return SpannableString(result.output)
    }

    private fun spannableFrom(themeNode: ThemeNode, token: Token): Spannable {
        return spannableFrom(themeNode, HtmlCompat.fromHtml(token.fullValue, HtmlCompat.FROM_HTML_MODE_COMPACT))
    }

    private fun spannableFrom(themeNode: ThemeNode, token: CharSequence): Spannable {
        val string = SpannableString(token)
        string.setSpan(TokenSpan(themeNode.foregroundColor), 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

}