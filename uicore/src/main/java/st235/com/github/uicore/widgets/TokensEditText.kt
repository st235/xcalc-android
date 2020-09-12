package st235.com.github.uicore.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import kotlin.math.max

typealias OnCarriageListener = (start: Int, end: Int) -> Unit

class TokensEditText : AppCompatEditText {

    private var programmaticallySelected: Boolean = false
    private var selectionPositionFromTail: Int = 0

    var onCarriageListener: OnCarriageListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        background = null
        showSoftInputOnFocus = false
    }

    override fun isSuggestionsEnabled(): Boolean {
        return false
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        programmaticallySelected = true
        super.setText(text, type)

        if (selectionStart != selectionEnd) {
            selectionPositionFromTail = 0
        }

        setSelection(getCursorPosition())
    }

    override fun setSelection(start: Int, stop: Int) {
        if (selectionStart == start && selectionEnd == stop) {
            return
        }

        super.setSelection(start, stop)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)

        if (!programmaticallySelected) {
            if (selStart == selEnd) {
                selectionPositionFromTail = (text?.length ?: 0) - selStart
            }
        }

        if (programmaticallySelected) {
            programmaticallySelected = false
        }

        onCarriageListener?.invoke(selStart, selEnd)
    }

    fun resetCursor() {
        selectionPositionFromTail = 0
    }

    private fun getCursorPosition(): Int {
        return max(0, (text?.length ?: 0) - selectionPositionFromTail)
    }
}
