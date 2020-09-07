package st235.com.github.calculator.presentation.calculator.keyboard.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.item_keyboard_ordinary_button.view.*
import st235.com.github.calculator.R
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardButton
import st235.com.github.calculator.presentation.calculator.keyboard.KeyboardLayoutDelegate
import st235.com.github.calculator.presentation.calculator.keyboard.OnButtonClickListener
import st235.com.github.calculator.presentation.calculator.keyboard.token.TokenButton
import st235.com.github.uicore.utils.RippleEffect
import st235.com.github.uicore.utils.toPx
import st235.com.github.uicore.widgets.KeyboardLayout

class WidgetButtonLayoutDelegate(
    private val context: Context,
    private val inflater: LayoutInflater,
    private val parent: KeyboardLayout,
    private val onClickListener: OnButtonClickListener
): KeyboardLayoutDelegate {

    override fun isApplicable(keyboardButton: KeyboardButton): Boolean =
        keyboardButton is WidgetButton

    override fun createView(
        keyboardButton: KeyboardButton
    ): View {
        keyboardButton as WidgetButton

        val buttonView = inflater.inflate(R.layout.item_keyboard_widget_button, parent, false)
        val buttonLayout = buttonView.findViewById<View>(R.id.buttonLayout)

        RippleEffect.attachTo(buttonLayout, keyboardButton.backgroundColor, keyboardButton.rippleColor, 12F.toPx())

        buttonView.isClickable = true
        buttonView.isFocusable = true
        buttonView.setOnClickListener {
            onClickListener(keyboardButton.id, false)
        }

        buttonView.buttonText.text = keyboardButton.token
        buttonView.buttonText.setTextColor(keyboardButton.foregroundColor)
        buttonView.buttonText.setTypeface(ResourcesCompat.getFont(context, R.font.montserrat_regular))
        return buttonView
    }

    override fun createLayoutParams(keyboardButton: KeyboardButton): KeyboardLayout.LayoutParams {
        val lp = KeyboardLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        lp.spanColumns = keyboardButton.spanColumns
        lp.heightWeight = 2
        return lp
    }
}
