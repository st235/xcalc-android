package st235.com.github.uicore.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import st235.com.github.uicore.R
import kotlin.math.max
import kotlin.math.min

class KeyboardLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var heightFactor: Float = 1F
    private val rowHeights = mutableMapOf<Int, Int>()

    var columns = 1
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

        columns = typedArray.getInt(R.styleable.KeyboardLayout_kl_columns, 1)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val proposedHeight = MeasureSpec.getSize(heightMeasureSpec)

        var height = 0
        var width = 0

        var rowWidth = 0
        var rowHeight = 0

        var itemsCounter = 0
        for (i in 0 until childCount) {
            if (itemsCounter % columns == 0) {
                rowHeights[(itemsCounter - 1) / columns] = rowHeight
                height += rowHeight
                width = max(width, rowWidth)

                rowHeight = 0
                rowWidth = 0
            }

            val childView = getChildAt(i)

            childView.measure(
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED)
            )

            val layoutParams = (childView.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

            itemsCounter += layoutParams.spanColumns
            rowWidth += (childView.measuredWidth + layoutParams.marginStart + layoutParams.marginEnd)
            rowHeight = max(rowHeight, layoutParams.heightWeight)
        }

        if (!rowHeights.containsKey((itemsCounter) / columns)) {
            rowHeights[(itemsCounter - 1) / columns] = rowHeight
            height += rowHeight
            width = max(width, rowWidth)
        }

        heightFactor = proposedHeight / height.toFloat()
        val itemWidth = measuredWidth / columns.toFloat()

        var i = 0
        itemsCounter = 0
        while (i < childCount) {
            val childView = getChildAt(i)

            val layoutParams = (childView.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

            val childWidth = (itemWidth * layoutParams.spanColumns).toInt()
            val childHeight = (rowHeights.getValue(itemsCounter / columns) * heightFactor).toInt()

            childView.measure(
                MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
            )


            itemsCounter += layoutParams.spanColumns
            i++
        }

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(calculateDesiredSize(widthMeasureSpec, width), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(calculateDesiredSize(heightMeasureSpec, height), MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val sizePerItem = measuredWidth / columns.toFloat()

        var x = 0F
        var y = 0F

        var i = 0
        var itemsCounter = 0
        while (i < childCount) {
            if (i != 0 && itemsCounter % columns == 0) {
                x = 0F
                y += rowHeights.getValue((itemsCounter - 1) / columns) * heightFactor
            }

            val childView = getChildAt(i)

            val layoutParams = (childView.layoutParams ?: generateDefaultLayoutParams()) as LayoutParams

            val childWidth = childView.measuredWidth + layoutParams.marginStart + layoutParams.marginEnd
            val childHeight = rowHeights.getValue(itemsCounter / columns) * heightFactor

            childView.layout(
                (x).toInt(),
                y.toInt(),
                (x + sizePerItem * layoutParams.spanColumns).toInt(),
                (y + childHeight).toInt()
            )

            x += sizePerItem * layoutParams.spanColumns
            itemsCounter += layoutParams.spanColumns
            i++
        }
    }

    private fun calculateDesiredSize(sizeSpec: Int, contentSize: Int): Int {
        val mode = MeasureSpec.getMode(sizeSpec)
        val size = MeasureSpec.getSize(sizeSpec)

        return when(mode) {
            MeasureSpec.UNSPECIFIED -> contentSize
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> min(size, contentSize)
            else -> throw IllegalArgumentException()
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
        var heightWeight: Int

        constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
            val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.KeyboardLayout_Layout
            )

            spanColumns = typedArray.getInt(R.styleable.KeyboardLayout_Layout_kl_spanColumns, 1)
            heightWeight = typedArray.getInt(R.styleable.KeyboardLayout_Layout_kl_heightWeight, 1)

            typedArray.recycle()
        }

        constructor(params: ViewGroup.LayoutParams): super(params) {
            if (params is LayoutParams) {
                spanColumns = params.spanColumns
                heightWeight = params.heightWeight
            } else {
                spanColumns = 1
                heightWeight = 1
            }
        }

        constructor(width: Int, height: Int): super(width, height) {
            spanColumns = 1
            heightWeight = 1
        }
    }
}