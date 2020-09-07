package st235.com.github.calculator.data

import st235.com.github.calculator.data.storage.KeyValueStorage
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator_core.Angles
import javax.inject.Inject

class SettingsManager @Inject constructor(
    private val keyValueStorage: KeyValueStorage,
    private val tokenService: TokenService
) {

    fun getAngle(): Angles {
        val angles = Angles.findById(
            id = keyValueStorage.getInt(
                PREFS_ANGLE_KEY,
                Angles.DEGREES.id
            )
        )

        tokenService.setAngleUnits(angles)
        return angles
    }

    fun changeAngle(): Angles {
        keyValueStorage[PREFS_ANGLE_KEY] = getAngle().opposite().id
        val units = getAngle()
        tokenService.setAngleUnits(units)
        return units
    }

    private fun Angles.opposite() = when(this) {
        Angles.DEGREES -> Angles.RADIANS
        Angles.RADIANS -> Angles.DEGREES
    }

    private companion object {
        const val PREFS_ANGLE_KEY = "prefs.angle"
    }
}
