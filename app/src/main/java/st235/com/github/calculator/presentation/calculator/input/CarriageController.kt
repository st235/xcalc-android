package st235.com.github.calculator.presentation.calculator.input

import st235.com.github.calculator_core.tokens.Token
import java.util.*
import javax.inject.Inject

typealias InputMapping = TreeMap<Int, Int>

class CarriageController @Inject constructor() {

    private var lastKnownCarriageIndex: CarriageIndex = CarriageIndex(0, 0)

    private val inputStartMapping: InputMapping = InputMapping()
    private val inputFinishMapping: InputMapping = InputMapping()

    init {
        consume(Collections.emptyList())
    }

    fun consume(tokens: List<Token>) {
        inputStartMapping.clear()
        inputFinishMapping.clear()

        var currentLength = 0
        for (i in tokens.indices) {
            val token = tokens[i]
            val tokenLength = token.length
            inputStartMapping[currentLength] = i
            currentLength += tokenLength
            inputFinishMapping[currentLength] = i
        }

        //terminal sign
        inputStartMapping[currentLength] = tokens.size
    }

    fun isIntervalSelected(): Boolean {
        return lastKnownCarriageIndex.isInterval()
    }

    fun findTokenBelow(): Int? {
        val carriageIndex = lastKnownCarriageIndex
        return inputFinishMapping.floorEntry(carriageIndex.start)?.value
    }

    fun findNextToken(): Int {
        val carriageIndex = lastKnownCarriageIndex

        val indexOfTokenAhead = inputStartMapping.ceilingEntry(carriageIndex.index())?.value

        if (indexOfTokenAhead == null) {
            //parked text editing
            return inputStartMapping.lastEntry()?.value ?: throw IllegalStateException("Cannot be null")
        }

        return indexOfTokenAhead
    }

    fun findNearestInclusiveInterval(): Pair<Int, Int> {
        val carriageIndex = lastKnownCarriageIndex

        val indexOfTokenBelow = inputStartMapping.floorEntry(carriageIndex.start)?.value
        val indexOfTokenAhead = inputStartMapping.ceilingEntry(carriageIndex.finish)?.value

        if (indexOfTokenBelow == null) {
            // parked text editing
            throw IllegalStateException()
        }

        if (indexOfTokenAhead == null) {
            return Pair(indexOfTokenBelow, inputStartMapping.lastEntry()?.value ?: throw IllegalStateException("Cannot be null"))
        }

        return Pair(indexOfTokenBelow, indexOfTokenAhead)
    }

    fun onCarriagePositionChanged(start: Int, finish: Int) {
        lastKnownCarriageIndex = CarriageIndex(
        start = start,
        finish = finish
        )
    }

    data class CarriageIndex(
        val start: Int,
        val finish: Int
    ) {

        fun index(): Int {
            if (isInterval()) {
                throw IllegalStateException()
            }

            return start
        }

        fun isInterval(): Boolean {
            return finish > start
        }

    }
}
