package st235.com.github.calculator.presentation.calculator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import st235.com.github.calculator.data.KeyboardManager
import st235.com.github.calculator.data.SettingsManager
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator.presentation.calculator.input.ScreenDataFactory
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator_core.Angles
import javax.inject.Inject

class CalculatorInteractor @Inject constructor(
    private val tokenProcessor: TokenService,
    private val keyboardManager: KeyboardManager,
    private val settingsManager: SettingsManager,
    private val screenDataFactory: ScreenDataFactory
) {

    private val screenDataSubject = PublishSubject.create<Unit>()

    fun appendIds(ids: Collection<String>) {
        tokenProcessor.append(ids)
        reloadData()
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
        tokenProcessor.remove()
        reloadData()
    }

    fun clear() {
        tokenProcessor.clear()
        reloadData()
    }

    fun observeScreenData(): Observable<CalculatorScreenData> {
        return screenDataSubject
            .map { tokenProcessor.evaluate() }
            .map { screenDataFactory.create(it) }
    }

    private fun reloadData() {
        screenDataSubject.onNext(Unit)
    }

}