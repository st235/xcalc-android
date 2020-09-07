package st235.com.github.calculator_core

import android.content.res.Resources
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import st235.com.github.calculator_core.tokens.Token

class TokenProcessorFactory(
    resources: Resources
) {

    private val gson = GsonBuilder().create()

    private val tokenLoader =
        TokenLoader(
            resources = resources,
            gson = gson
        )

    private var tokens: Map<String, Token>? = null

    @WorkerThread
    fun prepare(): TokenProcessor {
        this.tokens = tokenLoader.load(R.raw.tokens)
        val tokens = tokens ?: throw IllegalStateException("Cannot load tokens")
        return TokenProcessor(tokens)
    }
}
