package st235.com.github.calculator.presentation.calculator

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import st235.com.github.calculator.data.KeyboardManager
import st235.com.github.calculator.data.SettingsManager
import st235.com.github.calculator.presentation.calculator.input.CarriageController
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator.presentation.calculator.input.InputProcessor
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator_core.Angles
import javax.inject.Inject

class CalculatorInteractor @Inject constructor(
    private val tokenProcessor: TokenService,
    private val keyboardManager: KeyboardManager,
    private val settingsManager: SettingsManager,
    private val inputProcessor: InputProcessor,
    private val carriageController: CarriageController
) {

    private val screenDataSubject = PublishSubject.create<Unit>()

    fun appendIds(ids: Collection<String>) {
        if (carriageController.isIntervalSelected()) {
            val (start, finish) = carriageController.findNearestInclusiveInterval()
            tokenProcessor.replace(start, finish, ids)
        } else {
            val index = carriageController.findNextToken()
            tokenProcessor.insert(index, ids)
        }

        reloadData()
    }

    fun onCarriageChange(start: Int, finish: Int) {
        Log.d("Carriage", "Carriage position changed: $start - $finish")
        carriageController.onCarriagePositionChanged(start, finish)
    }

    fun observeKeyboard(): Observable<List<KeyboardButton>> {
        return keyboardManager.observeKeyboard()
    }

    fun getAngle(): Angles {
        return settingsManager.getAngle()
    }

    fun changeAngle(): Angles {
        val angles = settingsManager.changeAngle()
        reloadData()
        return angles
    }

    fun removeLast() {
        if (carriageController.isIntervalSelected()) {
            val (start, finish) = carriageController.findNearestInclusiveInterval()
            tokenProcessor.removeInterval(start, finish)
        } else {
            val index = carriageController.findTokenBelow()
            index?.let {
                tokenProcessor.remove(index)
            }
        }

        reloadData()
    }

    fun clear() {
        tokenProcessor.clear()
        reloadData()
    }

    fun observeScreenData(): Observable<CalculatorScreenData> {
        return screenDataSubject
            .map { tokenProcessor.evaluate() }
            .map {
                carriageController.consume(it.input)
                return@map it
            }
            .map { inputProcessor.process(it) }
    }

    private fun reloadData() {
        screenDataSubject.onNext(Unit)
    }

}