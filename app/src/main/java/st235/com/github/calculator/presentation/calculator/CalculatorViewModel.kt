package st235.com.github.calculator.presentation.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import st235.com.github.calculator.presentation.base.BaseViewModel
import st235.com.github.calculator.presentation.calculator.input.CarriageController
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.utils.applyComputation
import st235.com.github.calculator_core.Angles
import java.util.*
import javax.inject.Inject

class CalculatorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
): BaseViewModel() {

    private val buttonsLiveData = MutableLiveData<List<KeyboardButton>>()
    private val screenDataLiveData = MutableLiveData<CalculatorScreenData>()
    private val angleUnitsLiveData = MutableLiveData<Angles>()

    private var screenDataDisposable: Disposable? = null
    private var keyboardDisposable: Disposable? = null

    override fun onCreate() {
        angleUnitsLiveData.value = interactor.getAngle()

        keyboardDisposable = interactor.observeKeyboard()
            .applyComputation()
            .subscribe {
                buttonsLiveData.value = it
            }

        screenDataDisposable = interactor.observeScreenData()
            .applyComputation()
            .subscribe(
                {
                    screenDataLiveData.value = it
                },
                {
                    Log.e("Error", "Error", it)
                }
            )
    }

    fun observeButtons(): LiveData<List<KeyboardButton>> = buttonsLiveData

    fun observeScreenData(): LiveData<CalculatorScreenData> = screenDataLiveData

    fun observeAngleUnitsLiveData(): LiveData<Angles> = angleUnitsLiveData

    fun onNewTokenReceived(token: String) {
        interactor
            .appendIds(Collections.singleton(token))
    }

    fun onSelectionChanged(startSelection: Int, endSelection: Int) {
        interactor.onCarriageChange(startSelection, endSelection)
    }

    fun onChangeAngle() {
        angleUnitsLiveData.value = interactor.changeAngle()
    }

    fun onRemoveLastToken() {
        interactor.removeLast()
    }

    fun onClear() {
        interactor.clear()
    }

    fun onResult(output: String) {
        interactor.onResult(output)
    }

    override fun onCleared() {
        screenDataDisposable?.dispose()
        screenDataDisposable = null
        keyboardDisposable?.dispose()
        keyboardDisposable = null
    }
}
