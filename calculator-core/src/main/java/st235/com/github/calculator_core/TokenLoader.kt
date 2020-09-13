package st235.com.github.calculator_core

import android.content.res.Resources
import androidx.annotation.RawRes
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import st235.com.github.calculator_core.tokens.RawTokens
import st235.com.github.calculator_core.tokens.Token
import java.io.InputStreamReader
import java.util.*

internal class TokenLoader(
    private val gson: Gson,
    private val resources: Resources
) {

    private val rawTokensType = object :TypeToken<RawTokens>() {}

    @WorkerThread
    fun load(@RawRes tokensRes: Int): Map<String, Token> {
        val resourceInputStream = InputStreamReader(resources.openRawResource(tokensRes))
        val json = resourceInputStream.readText()
        val rawTokens: RawTokens? = gson.fromJson<RawTokens>(json, rawTokensType.type)

        if (rawTokens == null) {
            return Collections.emptyMap()
        }

        val tokensOutput = mutableMapOf<String, Token>()
        load(rawTokens, tokensOutput)

        return tokensOutput
    }

    private fun load(primaries: RawTokens, output: MutableMap<String, Token>) {
        for (rawToken in primaries) {
            val token = Token(
                rawToken.id,
                rawToken.value,
                rawToken.formattedValue,
                rawToken.inputValue,
                rawToken.length,
                rawToken.isSolid
            )
            output[token.id] = token
        }
    }
}
