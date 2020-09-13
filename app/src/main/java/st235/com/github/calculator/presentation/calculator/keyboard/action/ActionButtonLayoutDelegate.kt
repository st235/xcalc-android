package st235.com.github.calculator.presentation.calculator.keyboard.action

import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_keyboard_special_button.view.*
import st235.com.github.calculator.R
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardLayoutDelegate
import st235.com.github.calculator.presentation.calculator.keyboard.OnButtonClickListener
import st235.com.github.uicore.utils.RippleEffect
import st235.com.github.uicore.utils.toPx
import st235.com.github.uicore.widgets.KeyboardLayout

class ActionButtonLayoutDelegate(
    private val inflater: LayoutInflater,
    private val parent: KeyboardLayout,
    private val onClickListener: OnButtonClickListener
): KeyboardLayoutDelegate {

    override fun isApplicable(keyboardButton: KeyboardButton): Boolean = keyboardButton is ActionButton

    override fun createView(keyboardButton: KeyboardButton): View {
        keyboardButton as ActionButton

        val buttonView = inflater.inflate(R.layout.item_keyboard_special_button, parent, false)
        val buttonLayout = buttonView.findViewById<View>(R.id.buttonLayout)

        RippleEffect.attachTo(buttonLayout, keyboardButton.backgroundColor, keyboardButton.rippleColor, 16F.toPx())

        buttonView.isClickable = true
        buttonView.isFocusable = true
        buttonView.isLongClickable = true
        buttonView.isHapticFeedbackEnabled = true
        buttonView.setOnClickListener {
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            onClickListener(keyboardButton.id, false)
        }

        buttonView.setOnLongClickListener {
            onClickListener(keyboardButton.id, true)
            true
        }

        buttonView.specialText.text = keyboardButton.value
        buttonView.specialText.setTextColor(keyboardButton.foregroundColor)
        return buttonView
    }

    override fun createLayoutParams(keyboardButton: KeyboardButton): KeyboardLayout.LayoutParams {
        val lp = KeyboardLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        lp.spanColumns = keyboardButton.spanColumns
        lp.heightWeight = 3
        return lp
    }
}