package com.explorer.wallettest.RxTest

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--6:27 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class RxTest {

    fun combineTest() {
        val first = Observable.just(100)
        val second = Observable.just("200")
        Observable.zip(first, second,
            { t1, t2 -> "wo: $t1 -- $t2" })
            .subscribe({println(it)})

    }
}
fun main() {
    val t = RxTest()
    t.combineTest()
}