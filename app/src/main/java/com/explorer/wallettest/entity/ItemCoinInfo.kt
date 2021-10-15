package com.explorer.wallettest.entity

import com.explorer.wallettest.database.LocalStoreKey
import wallet.core.jni.Account
import wallet.core.jni.CoinType

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--3:24 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
data class ItemCoinInfo(
    val type: CoinType,
    val name: String,
    var isSupport: Boolean = false
)

data class UnWrapAccount (
    val coin: CoinType,
    val address: String,
    val derivationPath: String,
    val extendedPublicKey: String
)

data class TransferPageParams (
    val account: Account,
    val localStoreKey: LocalStoreKey
)
