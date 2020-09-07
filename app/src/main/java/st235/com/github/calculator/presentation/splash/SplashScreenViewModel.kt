package st235.com.github.calculator.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import st235.com.github.calculator.startup.MainService
import st235.com.github.calculator.presentation.base.BaseViewModel
import st235.com.github.calculator.utils.applyComputation
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val mainService: MainService
) : BaseViewModel() {

    private val initializedLiveData = MutableLiveData<Boolean>()

    private var initializationDisposable: Disposable? = null

    override fun onCreate() {
        initializationDisposable = mainService.initialize()
            .applyComputation()
            .subscribe(
                {
                    initializedLiveData.value = it
                },
                {

                }
            )
    }

    fun observeInitialized(): LiveData<Boolean> = initializedLiveData

    override fun onCleared() {
        initializationDisposable?.dispose()
        initializationDisposable = null
    }
}
