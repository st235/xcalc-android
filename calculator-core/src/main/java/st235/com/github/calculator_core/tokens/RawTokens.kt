package st235.com.github.calculator_core.tokens

import com.google.gson.annotations.SerializedName

internal typealias RawTokens = List<RawToken>

internal data class RawToken(
    @SerializedName("id")
    val id: String,
    @SerializedName("rawValue")
    val value: String,
    @SerializedName("formattedValue")
    val formattedValue: String?,
    @SerializedName("inputValue")
    val inputValue: String?
)
