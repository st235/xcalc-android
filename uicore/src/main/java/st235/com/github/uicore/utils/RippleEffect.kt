package st235.com.github.uicore.utils

import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.util.StateSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px

object RippleEffect {

    fun attachTo(
        view: View,
        @ColorInt backgroundColor: Int,
        @ColorInt rippleColor: Int,
        @Px backgroundRoundRadius: Float
    ) {
        view.background =
            getPressedColorRippleDrawable(
                backgroundColor,
                rippleColor,
                backgroundRoundRadius
            )
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getPressedColorRippleDrawable(
        @ColorInt normalColor: Int,
        @ColorInt pressedColor: Int,
        @Px backgroundRoundRadius: Float
    ): Drawable = RippleDrawable(
        createColorStateList(
            pressedColor
        ),
        createShapeDrawable(
            normalColor,
            backgroundRoundRadius
        ),
        null
    ).apply {
        val states =
            arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(pressedColor)
        val colorStateList = ColorStateList(states, colors)
        setColor(colorStateList)
    }

    private fun createStateListDrawable(
        @ColorInt backgroundColor: Int,
        @ColorInt rippleColor: Int,
        @Px backgroundRoundRadius: Float
    ) =
        StateListDrawable().apply {
            addState(
                intArrayOf(
                    android.R.attr.state_pressed
                ),
                createShapeDrawable(
                    rippleColor,
                    backgroundRoundRadius
                )
            )
            addState(
                StateSet.WILD_CARD,
                createShapeDrawable(
                    backgroundColor,
                    backgroundRoundRadius
                )
            )
        }

    private fun createShapeDrawable(
        @ColorInt backgroundColor: Int,
        @Px roundRadius: Float
    ) = ShapeDrawable(
        RoundRectShape(
            floatArrayOf(
                roundRadius, roundRadius,
                roundRadius, roundRadius,
                roundRadius, roundRadius,
                roundRadius, roundRadius
            ),
            null,
            null
        )
    ).apply {
        paint.color = backgroundColor
    }

    private fun createColorStateList(@ColorInt pressedColor: Int) =
        ColorStateList.valueOf(pressedColor)
}
