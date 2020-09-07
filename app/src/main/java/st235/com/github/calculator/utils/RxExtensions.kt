package st235.com.github.calculator.utils

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applyComputation(): Single<T> {
    return subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyComputation(): Observable<T> {
    return subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
}
