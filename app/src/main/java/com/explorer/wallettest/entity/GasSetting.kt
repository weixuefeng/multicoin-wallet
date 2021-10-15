package com.explorer.wallettest.entity

import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--6:55 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
data class GasSetting(
    val gasPrice: BigInteger,
    val gasLimit: BigInteger,
    val nonce: BigInteger
)
