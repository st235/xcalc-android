package st235.com.github.calculator.utils

import android.content.Context
import android.content.res.Configuration

fun Context.isInLandscape(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
