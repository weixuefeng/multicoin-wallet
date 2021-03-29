package com.explorer.wallettest.service

import io.reactivex.Single
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--3:59 PM
 * @description Asset Service
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
interface AssetService {

    fun getBalance(coinName: String, address: String): Single<BigInteger>

    fun getTransactionCount(coinName: String, address: String): Single<BigInteger>

    fun getTransactionGasPrice(coinName: String): Single<BigInteger>

    fun getTransactionGasLimit(coinName: String, transaction: Transaction): Single<BigInteger>

    fun buildTransaction(coinName: String, address: String)

    fun sendRawTransaction(coinName: String, transaction: String)
}