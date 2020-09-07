package st235.com.github.calculator.startup

import io.reactivex.Single
import st235.com.github.calculator_core.Angles
import st235.com.github.calculator_core.ProcessingResult
import st235.com.github.calculator_core.TokenProcessor
import st235.com.github.calculator_core.TokenProcessorFactory
import st235.com.github.calculator_core.tokens.Token
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenService @Inject constructor(
    private val tokenProcessorFactory: TokenProcessorFactory
) {

    @Volatile
    private var tokenProcessor: TokenProcessor? = null

    fun initialize(): Single<Boolean> {
        return Single.create { emitter ->
            synchronized(this) {
                this.tokenProcessor = tokenProcessorFactory.prepare()
            }
            emitter.onSuccess(true)
        }
    }

    fun setAngleUnits(units: Angles) {
        requireTokenProcessor().setAngleUnits(units)
    }

    fun changeAngleUnits(): Angles {
        return requireTokenProcessor().changeAngleUnits()
    }

    fun findTokenById(id: String): Token {
        return requireTokenProcessor().findTokenById(id)
    }

    fun append(tokens: Collection<String>) {
        return requireTokenProcessor().appendAll(tokens)
    }

    fun remove() {
        requireTokenProcessor().remove()
    }

    fun clear() {
        requireTokenProcessor().clear()
    }

    fun evaluate(): ProcessingResult {
        return requireTokenProcessor().evaluate()
    }

    private fun requireTokenProcessor(): TokenProcessor {
        return tokenProcessor!!
    }
}