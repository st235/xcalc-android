package st235.com.github.uicore.themes

import com.google.gson.annotations.SerializedName

data class Themes(
    @SerializedName("themes")
    val themes: List<Theme>
)