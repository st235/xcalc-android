package st235.com.github.calculator.presentation.calculator.keyboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.item_keyboard_ordinary_button.view.*
import kotlinx.android.synthetic.main.item_keyboard_special_button.view.*
import st235.com.github.calculator.R
import st235.com.github.calculator.presentation.calculator.keyboard.action.ActionButtonLayoutDelegate
import st235.com.github.calculator.presentation.calculator.keyboard.token.TokenButtonLayoutDelegate
import st235.com.github.calculator.presentation.calculator.keyboard.widget.WidgetButtonLayoutDelegate
import st235.com.github.uicore.utils.RippleEffect
import st235.com.github.uicore.utils.toPx
import st235.com.github.uicore.widgets.KeyboardLayout

class KeyboardLayoutFactory(
    context: Context,
    private val keyboardLayout: KeyboardLayout,
    onTokenClickListener: OnButtonClickListener,
    onActionClickListener: OnButtonClickListener
) {

    private val inflater = LayoutInflater.from(context)

    private val delegates = listOf(
        ActionButtonLayoutDelegate(inflater, keyboardLayout, onActionClickListener),
        TokenButtonLayoutDelegate(context, inflater, keyboardLayout, onTokenClickListener),
        WidgetButtonLayoutDelegate(context, inflater, keyboardLayout, onTokenClickListener)
    )

    fun attach(buttons: List<KeyboardButton>) {
        keyboardLayout.removeAllViews()

        for (button in buttons) {
            attach(button)
        }
    }

    private fun attach(button: KeyboardButton) {
        val delegate = delegates.find { it.isApplicable(button) }
            ?: throw IllegalStateException("Cannot find delegate to resolve this button ${button::class.simpleName}")

        val view = delegate.createView(button)
        val layoutParams = delegate.createLayoutParams(button)

        keyboardLayout.addView(view, layoutParams)
    }
}