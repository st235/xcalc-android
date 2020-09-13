package st235.com.github.uicore.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import st235.com.github.uicore.R
import kotlin.math.abs
import kotlin.math.max

class KeyboardLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var heightFactor: Float = 1F

    var columnsCount = 1
    set(value) {
        if (value <= 0) {
            throw IllegalArgumentException()
        }

        field = value
        requestLayout()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.KeyboardLayout
        )

        columnsCount = typedArray.getInt(R.styleable.KeyboardLayout_kl_columns, 1)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        var index = 0
        var childAspectHeight = 0F

        while(index < childCount) {

            var levelIndex = 0
            var levelAspectHeight = 0F
            while (levelIndex < columnsCount && index < childCount) {
                val child = getChildAt(index)
                val layoutParams = (child.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

                if (levelIndex + layoutParams.spanColumns > columnsCount) {
                    break
                }

                levelAspectHeight = max(levelAspectHeight, layoutParams.aspectHeight)
                levelIndex += layoutParams.spanColumns
                index++
            }

            childAspectHeight += levelAspectHeight
        }

        val itemWidth = parentWidth / columnsCount
        val itemHeight = parentHeight / childAspectHeight

        index = 0
        while(index < childCount) {

            var levelIndex = 0
            while (levelIndex < columnsCount && index < childCount) {
                val child = getChildAt(index)
                val layoutParams = (child.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

                if (levelIndex + layoutParams.spanColumns > columnsCount) {
                    break
                }

                val w = itemWidth * layoutParams.spanColumns
                val h = (layoutParams.aspectHeight * itemHeight).toInt()

                child.measure(
                    MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
                )

                levelIndex += layoutParams.spanColumns
                index++

            }
        }

        setMeasuredDimension(parentWidth, parentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentWidth = abs(l - r)
        val parentHeight = abs(b - t)

        var index = 0
        var childAspectHeight = 0F

        while(index < childCount) {

            var levelIndex = 0
            var levelAspectHeight = 0F
            while (levelIndex < columnsCount && index < childCount) {
                val child = getChildAt(index)
                val layoutParams = (child.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

                if (levelIndex + layoutParams.spanColumns > columnsCount) {
                    break
                }

                levelAspectHeight = max(levelAspectHeight, layoutParams.aspectHeight)
                levelIndex += layoutParams.spanColumns
                index++
            }

            childAspectHeight += levelAspectHeight
        }

        val itemWidth = parentWidth / columnsCount
        val itemHeight = parentHeight / childAspectHeight

        var x = 0
        var y = 0

        index = 0
        while(index < childCount) {

            var levelIndex = 0
            var levelHeight = 0
            while (levelIndex < columnsCount && index < childCount) {
                val child = getChildAt(index)
                val layoutParams = (child.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

                if (levelIndex + layoutParams.spanColumns > columnsCount) {
                    break
                }

                val w = itemWidth * layoutParams.spanColumns
                val h = (layoutParams.aspectHeight * itemHeight).toInt()

                child.layout(
                    x,
                    y,
                    x + w,
                    y + h
                )

                levelIndex += layoutParams.spanColumns
                levelHeight = max(levelHeight, h)
                index++

                x += w
            }

            x = 0
            y += levelHeight
        }

    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams? {
        return LayoutParams(
            context,
            attrs
        )
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams? {
        return LayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams? {
        return LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    class LayoutParams: MarginLayoutParams {

        var spanColumns: Int
        var aspectHeight: Float

        constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
            val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.KeyboardLayout_Layout
            )

            spanColumns = typedArray.getInt(R.styleable.KeyboardLayout_Layout_kl_spanColumns, 1)
            aspectHeight = typedArray.getFloat(R.styleable.KeyboardLayout_Layout_kl_aspectHeight, 1F)

            typedArray.recycle()
        }

        constructor(params: ViewGroup.LayoutParams): super(params) {
            if (params is LayoutParams) {
                spanColumns = params.spanColumns
                aspectHeight = params.aspectHeight
            } else {
                spanColumns = 1
                aspectHeight = 1F
            }
        }

        constructor(width: Int, height: Int): super(width, height) {
            spanColumns = 1
            aspectHeight = 1F
        }
    }
}