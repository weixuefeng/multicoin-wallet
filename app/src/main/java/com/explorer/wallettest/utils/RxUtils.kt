package com.explorer.wallettest.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--5:32 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */

fun <T> RxUD(observer: Consumer<T>): ObservableTransformer<T, T> =
    ObservableTransformer<T, T> { upstream ->
        val observeOn =
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observeOn.subscribe(observer)
        observeOn
    }