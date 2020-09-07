package st235.com.github.uicore.widgets

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.math.MathUtils.clamp
import androidx.core.view.ViewCompat
import kotlin.math.min
import st235.com.github.uicore.R
import st235.com.github.uicore.utils.RippleEffect
import st235.com.github.uicore.utils.toPx

class RoundButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val bounds = Rect()

    @ColorInt
    var bgColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            invalidateRippleBackground()
        }

    @ColorInt
    var rippleColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            invalidateRippleBackground()
        }

    @Px
    var bgRoundRadius: Float = 0F
        set(value) {
            field = value
            invalidateRippleBackground()
            invalidateOutline()
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundButton)

        bgColor = typedArray.getColor(R.styleable.RoundButton_rb_backgroundColor, Color.TRANSPARENT)
        rippleColor = typedArray.getColor(R.styleable.RoundButton_rb_rippleColor, Color.TRANSPARENT)
        bgRoundRadius =
            typedArray.getDimension(R.styleable.RoundButton_rb_backgroundRoundRadius, 0F)
        setElevationCompat(typedArray.getDimension(R.styleable.RoundButton_rb_elevation, 0F))

        val allCaps = typedArray.getBoolean(R.styleable.RoundButton_rb_textAllCaps, true)
        val paddingVertical = typedArray.getDimension(R.styleable.RoundButton_rb_paddingVertical, 16F.toPx()).toInt()
        val paddingHorizontal = typedArray.getDimension(R.styleable.RoundButton_rb_paddingHorizontal, 16F.toPx()).toInt()

        typedArray.recycle()

        isClickable = true
        isFocusable = true
        isAllCaps = true
        gravity = Gravity.CENTER
        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
        isAllCaps = allCaps

        outlineProvider = RoundButtonOutlineProvider()
        clipToOutline = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(0, 0, w, h)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        if (isEnabled) {
            alpha = 1F
        } else {
            alpha = 0.5F
        }
    }

    fun setElevationCompat(@Px elevation: Float) {
        ViewCompat.setElevation(this, elevation)
    }

    private fun invalidateRippleBackground() {
        RippleEffect.attachTo(this, bgColor, rippleColor, bgRoundRadius)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private inner class RoundButtonOutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(bounds, clamp(bgRoundRadius, 0F, min(height, width) / 2F))
        }
    }
}
