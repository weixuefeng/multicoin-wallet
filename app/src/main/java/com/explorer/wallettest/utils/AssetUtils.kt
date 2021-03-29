package com.explorer.wallettest.utils

import wallet.core.jni.CoinType
import java.lang.Exception

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--4:05 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
object AssetUtils {
    fun checkCoinName(name: String, coinType: CoinType) {
        if(name != coinType.name) {
            throw CoinTypeErrorException("name is :$name, coin type name is: ${coinType.name}")
        }
    }
}

class CoinTypeErrorException(msg: String): Exception(msg)