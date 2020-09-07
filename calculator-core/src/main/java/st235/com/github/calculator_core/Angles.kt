package st235.com.github.calculator_core

import st235.com.github.xcalc_android_wrapper.AngleUnits

enum class Angles(
    val id: Int
) {
    RADIANS(0),
    DEGREES(1);

    companion object {

        fun findById(id: Int) = values().find { it.id == id } ?:
                throw IllegalArgumentException("Cannot find angle unit with id: $id")

    }
}

internal fun Angles.toAngleUnits() = when(this) {
    Angles.DEGREES -> AngleUnits.DEG
    Angles.RADIANS -> AngleUnits.RADS
}

internal fun AngleUnits.toAngles() = when(this) {
    AngleUnits.RADS -> Angles.RADIANS
    AngleUnits.DEG -> Angles.DEGREES
}
