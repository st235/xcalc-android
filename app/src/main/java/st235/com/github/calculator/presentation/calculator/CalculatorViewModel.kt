package st235.com.github.calculator.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import st235.com.github.calculator.presentation.base.BaseViewModel
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.utils.applyComputation
import st235.com.github.calculator_core.Angles
import java.util.*
import javax.inject.Inject

class CalculatorViewModel @Inject constructor(
    private val calculatorInteractor: CalculatorInteractor
): BaseViewModel() {

    private val buttonsLiveData = MutableLiveData<List<KeyboardButton>>()
    private val screenDataLiveData = MutableLiveData<CalculatorScreenData>()
    private val angleUnitsLiveData = MutableLiveData<Angles>()

    private var screenDataDisposable: Disposable? = null
    private var keyboardDisposable: Disposable? = null

    override fun onCreate() {
        angleUnitsLiveData.value = calculatorInteractor.getAngle()

        keyboardDisposable = calculatorInteractor.observeKeyboard()
            .applyComputation()
            .subscribe {
                buttonsLiveData.value = it
            }

        screenDataDisposable = calculatorInteractor.observeScreenData()
            .applyComputation()
            .subscribe(
                {
                    screenDataLiveData.value = it
                },
                {
                }
            )
    }

    fun observeButtons(): LiveData<List<KeyboardButton>> = buttonsLiveData

    fun observeScreenData(): LiveData<CalculatorScreenData> = screenDataLiveData

    fun observeAngleUnitsLiveData(): LiveData<Angles> = angleUnitsLiveData

    fun onNewTokenReceived(token: String) {
        calculatorInteractor
            .appendIds(Collections.singleton(token))
    }

    fun onChangeAngle() {
        angleUnitsLiveData.value = calculatorInteractor.changeAngle()
    }

    fun onRemoveLastToken() {
        calculatorInteractor.removeLast()
    }

    fun onClear() {
        calculatorInteractor.clear()
    }

    fun onEquals() {

    }

    override fun onCleared() {
        screenDataDisposable?.dispose()
        screenDataDisposable = null
        keyboardDisposable?.dispose()
        keyboardDisposable = null
    }
}
