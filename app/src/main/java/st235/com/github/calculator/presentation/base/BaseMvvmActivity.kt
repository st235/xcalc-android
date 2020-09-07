package st235.com.github.calculator.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseMvvmActivity<T: BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory

    protected val viewModel: T by lazy {
        onCreateViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onShouldInjectDependencies()
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    abstract fun onShouldInjectDependencies()

    abstract fun onCreateViewModel(): T

    protected inline fun <reified T: BaseViewModel> provideViewModel(): T {
        return ViewModelProvider(this, viewModelFactory).get(T::class.java)
    }
}