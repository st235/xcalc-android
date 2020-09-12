package st235.com.github.uicore.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import kotlin.math.roundToInt


class TokenSpan(
    @ColorInt private val textColor: Int
): ReplacementSpan() {

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val prevColor = paint.color
        paint.color = textColor
        text?.let { canvas.drawText(it, start, end, x, y.toFloat(), paint) }
        paint.color = prevColor
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?, start: Int, end: Int,
        fm: FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).roundToInt()
    }

}