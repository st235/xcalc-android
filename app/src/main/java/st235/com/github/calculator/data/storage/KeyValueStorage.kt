package st235.com.github.calculator.data.storage

import android.content.Context
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStorage @Inject constructor(
    context: Context
) {

    private val sharedPreferences =
        context.getSharedPreferences("xcalc.prefs", Context.MODE_PRIVATE)

    operator fun set(key: String, value: Int) {
        sharedPreferences.edit()
            .putInt(key, value)
            .apply()
    }

    operator fun set(key: String, value: List<String>) {
        sharedPreferences.edit()
            .putString(key, value.joinToString())
            .apply()
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun getStringList(key: String, defValue: List<String>): List<String> {
        return (sharedPreferences.getString(key, defValue.joinToString()) ?: "")
            .splitToSequence(", ")
            .toList()
    }
}