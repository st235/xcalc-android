package st235.com.github.uicore.themes

import com.google.gson.annotations.SerializedName

data class Theme(
    @SerializedName("operators")
    val operators: ThemeNode,
    @SerializedName("primary")
    val primary: ThemeNode,
    @SerializedName("error")
    val error: ThemeNode,
    @SerializedName("result")
    val result: ThemeNode,
    @SerializedName("info")
    val info: ThemeNode,
    @SerializedName("hints")
    val hints: ThemeNode,
    @SerializedName("window")
    val appearance: ThemeNode,
    @SerializedName("default")
    val default: Boolean?
)