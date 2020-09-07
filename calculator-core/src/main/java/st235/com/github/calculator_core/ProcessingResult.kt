package st235.com.github.calculator_core

import st235.com.github.calculator_core.tokens.Token

data class ProcessingResult(
    val input: List<Token>,
    val output: String,
    val balanceSuffix: String,
    val wasError: Boolean
)
