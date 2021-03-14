package com.explorer.wallettest.logger

import android.util.Log
import com.explorer.wallettest.BuildConfig

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--3:02 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
object Logger {

    private const val defaultTag = "NewPay"

    fun e(msg: String, TAG: String = defaultTag) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg)
        }
    }

    fun <T> d(msg: T, TAG: String = defaultTag) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg.toString())
        }
    }
}