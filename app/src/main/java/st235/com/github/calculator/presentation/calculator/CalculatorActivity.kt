package st235.com.github.calculator.presentation.calculator

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_calculator.*
import st235.com.github.calculator.di.DiInjector
import st235.com.github.calculator.R
import st235.com.github.calculator.data.KeyboardManager
import st235.com.github.calculator.presentation.base.BaseMvvmActivity
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardLayoutFactory
import st235.com.github.calculator_core.Angles
import st235.com.github.calculator_core.utils.TokenHelper

class CalculatorActivity : BaseMvvmActivity<CalculatorViewModel>() {

    private lateinit var keyboardLayoutFactory: KeyboardLayoutFactory

    private val onSpecialButtonClicked = { id: String, isLongClick: Boolean ->
        when {
            id == KeyboardManager.ID_CLEAR && !isLongClick -> viewModel.onRemoveLastToken()
            id == KeyboardManager.ID_CLEAR && isLongClick -> viewModel.onClear()
        }
    }

    private val onOrdinaryButtonClicked = { token: String, _: Boolean ->
        viewModel.onNewTokenReceived(token)
    }

    override fun onShouldInjectDependencies() {
        DiInjector.inject(this)
    }

    override fun onCreateViewModel(): CalculatorViewModel {
        return provideViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        keyboardLayoutFactory =
            KeyboardLayoutFactory(
                this,
                keyboardLayout,
                onOrdinaryButtonClicked,
                onSpecialButtonClicked
            )

        angleUnitsSelector.setOnClickListener {
            viewModel.onChangeAngle()
        }

        viewModel.observeAngleUnitsLiveData()
            .observe(this, Observer {
                angleUnitsSelector.text = it.getName()
            })

        viewModel.observeButtons()
            .observe(this, Observer { buttons ->
                keyboardLayoutFactory.attach(buttons)
            })

        viewModel.observeScreenData()
            .observe(this, Observer { screenData ->
                inputField.setText(screenData.input, TextView.BufferType.SPANNABLE)
                outputField.setText(screenData.output, TextView.BufferType.SPANNABLE)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    private fun Angles.getName() = when(this) {
        Angles.RADIANS -> getString(R.string.calculator_angle_rads)
        Angles.DEGREES -> getString(R.string.calculator_angle_deg)
    }
}