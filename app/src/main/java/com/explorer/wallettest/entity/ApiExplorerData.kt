package com.explorer.wallettest.entity

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--2:52 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
data class TokenListData(
    val balance: String,
    val contractAddress: String,
    val decimals: String,
    val name: String,
    val symbol: String,
    val type: String
)