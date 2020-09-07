package st235.com.github.calculator.presentation.calculator.keyboard

import android.view.View
import st235.com.github.calculator.presentation.calculator.keyboard.token.TokenButton
import st235.com.github.uicore.widgets.KeyboardLayout

typealias OnButtonClickListener = (id: String, isLongClick: Boolean) -> Unit

interface KeyboardLayoutDelegate {

    fun isApplicable(keyboardButton: KeyboardButton): Boolean

    fun createView(keyboardButton: KeyboardButton): View

    fun createLayoutParams(keyboardButton: KeyboardButton): KeyboardLayout.LayoutParams

}