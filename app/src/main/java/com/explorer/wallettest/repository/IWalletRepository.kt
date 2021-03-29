package com.explorer.wallettest.repository

import io.reactivex.Single
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--6:08 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
interface IWalletRepository {

    fun getGasPrice(coinName: String): Single<BigInteger>

    fun estimateGas(coinName: String, transaction: Transaction): Single<BigInteger>
}