package st235.com.github.uicore.themes

import android.graphics.Color
import androidx.annotation.ColorInt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class ThemeNode(
    @ColorInt val foregroundColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val rippleColor: Int
) {
    class Deserializer: JsonDeserializer<ThemeNode?> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): ThemeNode? {
            if (json == null) {
                return null
            }

            val jsonObject = json.asJsonObject

            val foregroundColor = Color.parseColor(jsonObject["foreground"].asString)
            val backgroundColor = if (jsonObject.has("background")) Color.parseColor(jsonObject["background"].asString) else Color.TRANSPARENT
            val rippleColor = Color.parseColor(jsonObject["ripple"].asString)

            return ThemeNode(foregroundColor = foregroundColor, backgroundColor = backgroundColor, rippleColor = rippleColor)
        }
    }
}