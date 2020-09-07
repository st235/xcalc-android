package st235.com.github.calculator.startup

import io.reactivex.Single
import javax.inject.Inject

class MainService @Inject constructor(
    private val tokenService: TokenService
) {

    fun initialize(): Single<Boolean> {
        return tokenService.initialize()
    }
}