package st235.com.github.calculator.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class BaseViewModelFactory @Inject constructor(
    private val models: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = models[modelClass]
        val model = provider?.get()

        if (model == null) {
            throw IllegalArgumentException("Cannot find view model")
        }

        return model as T
    }
}