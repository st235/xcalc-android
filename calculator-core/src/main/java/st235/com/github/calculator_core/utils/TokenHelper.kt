package st235.com.github.calculator_core.utils

import st235.com.github.calculator_core.tokens.Token

object TokenHelper {

    const val ID_NUM_0 = "0"
    const val ID_NUM_1 = "1"
    const val ID_NUM_2 = "2"
    const val ID_NUM_3 = "3"
    const val ID_NUM_4 = "4"
    const val ID_NUM_5 = "5"
    const val ID_NUM_6 = "6"
    const val ID_NUM_7 = "7"
    const val ID_NUM_8 = "8"
    const val ID_NUM_9 = "9"

    const val ID_CONST_E = "e"
    const val ID_CONST_PI = "pi"

    const val ID_OPEN_PARENTHESIS = "open_parenthesis"
    const val ID_CLOSE_PARENTHESIS = "close_parenthesis"

    const val ID_DOT = "dot"

    const val ID_OP_PLUS = "op_plus"
    const val ID_OP_MINUS = "op_minus"
    const val ID_OP_MULTIPLY = "op_multiply"
    const val ID_OP_DIVIDE = "op_divide"
    const val ID_OP_POWER = "op_pow"
    const val ID_OP_PERCENT = "op_percent"
    const val ID_OP_FACTORIAL = "op_fact"

    const val ID_FUN_COS = "cos"
    const val ID_FUN_SIN = "sin"
    const val ID_FUN_TAN = "tan"
    const val ID_FUN_LN = "ln"
    const val ID_FUN_LOG2 = "log2"
    const val ID_FUN_LOG10 = "log10"
    const val ID_FUN_SQRT = "sqrt"
    const val ID_FUN_EXP = "exp"

    private val OPERATORS_LIST = setOf(
        ID_OP_PLUS,
        ID_OP_MINUS,
        ID_OP_MULTIPLY,
        ID_OP_DIVIDE,
        ID_OP_POWER,
        ID_OP_PERCENT,
        ID_OP_FACTORIAL
    )

    fun isOperator(token: Token) = isOperator(token.id)

    fun isOperator(id: String): Boolean {
        return OPERATORS_LIST.contains(id)
    }

    internal fun getParenthesisCountToBalance(text: CharSequence): Int {
        var balance = 0
        for (i in text.indices) {
            if (text[i] == '(') {
                balance++
            } else if (text[i] == ')') {
                balance--
            }
        }
        return balance
    }

}
