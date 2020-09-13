package st235.com.github.calculator_core.tokens

typealias Tokens = List<Token>

class Token(
    internal val id: String,
    internal val rawValue: String,
    private val formattedString: String?,
    private val inputString: String?,
    val length: Int,
    val isSolid: Boolean
) {

    val shortValue: String by lazy {
        return@lazy formattedString ?: rawValue
    }

    val fullValue: String by lazy {
        return@lazy inputString ?: rawValue
    }

}

