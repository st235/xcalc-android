package st235.com.github.calculator_core

import st235.com.github.calculator_core.tokens.Token
import st235.com.github.calculator_core.utils.TokenHelper
import st235.com.github.xcalc_android_wrapper.AngleUnits
import st235.com.github.xcalc_android_wrapper.CalculationStatus
import st235.com.github.xcalc_android_wrapper.XCalc
import java.util.*
import kotlin.collections.ArrayList

class TokenProcessor(
    private val tokens: Map<String, Token>
) {

    private val xcalc = XCalc(angleUnits = AngleUnits.DEG)
    private val tokensList = LinkedList<Token>()

    fun setAngleUnits(units: Angles) {
        xcalc.changeAngleUnits(units.toAngleUnits())
    }

    fun changeAngleUnits(): Angles {
        when (xcalc.getAngleUnits()) {
            AngleUnits.DEG -> xcalc.changeAngleUnits(AngleUnits.RADS)
            AngleUnits.RADS -> xcalc.changeAngleUnits(AngleUnits.DEG)
        }

        return xcalc.getAngleUnits().toAngles()
    }

    fun findTokenById(id: String): Token {
        return tokens[id] ?: throw IllegalArgumentException("Cannot find token with id: $id")
    }

    fun insertAll(position: Int, ids: Collection<String>) {
        tokensList.addAll(position, ids.map { findTokenById(it) })
    }

    fun replaceAll(startIndex: Int, finishIndex: Int, ids: Collection<String>) {
        val newTokens = mutableListOf<Token>()

        var index = 0

        while (index < tokensList.size && index < startIndex) {
            newTokens.add(tokensList[index])
            index++
        }

        newTokens.addAll(ids.map { findTokenById(it) })

        index = finishIndex
        while (index < tokensList.size) {
            newTokens.add(tokensList[index])
            index++
        }

        tokensList.clear()
        tokensList.addAll(newTokens)
    }

    fun remove(index: Int) {
        if (tokensList.isEmpty()
            || index >= tokensList.size) {
            return
        }

        tokensList.removeAt(index)
    }

    fun removeInterval(start: Int, finish: Int) {
        if (tokensList.isEmpty()
            || start > finish) {
            return
        }

        val newTokens = mutableListOf<Token>()

        for (i in tokensList.indices) {
            if (i !in start until finish) {
                newTokens.add(tokensList[i])
            }
        }

        tokensList.clear()
        tokensList.addAll(newTokens)
    }

    fun clear() {
        tokensList.clear()
    }

    fun evaluate(): ProcessingResult {
        val inputTokens = ArrayList(tokensList)
        val rawInput = createXCalcInput()
        val countToBalance = TokenHelper.getParenthesisCountToBalance(rawInput)
        val balanceSuffix = createBalanceSuffix(countToBalance)
        val result = xcalc.evaluate(input = rawInput + balanceSuffix)

        return ProcessingResult(
            input = inputTokens,
            output = result.output ?: "",
            balanceSuffix = balanceSuffix,
            wasError = result.calculationStatus != CalculationStatus.OK
        )
    }

    private fun createXCalcInput(): String {
        val stringBuilder = StringBuilder()
        for (token in tokensList) {
            stringBuilder.append(token.rawValue)
        }
        return stringBuilder.toString()
    }

    private fun createBalanceSuffix(count: Int): String {
        val builder = StringBuilder()

        for (i in 0 until count) {
            builder.append(')')
        }
        return builder.toString()
    }

}