package st235.com.github.uicore.themes

import android.content.res.Resources
import androidx.annotation.RawRes
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import java.io.InputStreamReader

class ThemeExtractor(
    private val resources: Resources
) {

    val gson = GsonBuilder()
        .registerTypeAdapter(ThemeNode::class.java, ThemeNode.Deserializer())
        .create()

    @WorkerThread
    fun loadTheme(@RawRes themeId: Int): List<Theme> {
        val themeJson = InputStreamReader(resources.openRawResource(themeId))
        return gson.fromJson(themeJson, Themes::class.java).themes
    }
}