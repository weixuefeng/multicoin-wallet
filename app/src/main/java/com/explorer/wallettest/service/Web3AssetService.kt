package com.explorer.wallettest.service

import com.explorer.wallettest.utils.AssetUtils
import io.reactivex.Single
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import wallet.core.jni.CoinType
import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--4:03 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class Web3AssetService(private val web3j: Web3j): AssetService {

    override fun getBalance(coinName: String, address: String): Single<BigInteger> {
        return Single.fromCallable { web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance }

    }

    override fun getTransactionCount(coinName: String, address: String): Single<BigInteger> {
        return Single.fromCallable { web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send().transactionCount }
    }

    override fun getTransactionGasPrice(coinName: String): Single<BigInteger> {
        return Single.fromCallable {
            val res = web3j.ethGasPrice().send().gasPrice
            res
        }
    }

    override fun getTransactionGasLimit(coinName: String, transaction: Transaction): Single<BigInteger> {
        return Single.fromCallable { web3j.ethEstimateGas(transaction).send().amountUsed }
    }

    override fun buildTransaction(coinName: String, address: String) {
//        val signInput = NewChain.SigningInput.newBuilder().apply {
//            this.privateKey = ByteString.copyFrom(priv.data())
//            toAddress = mToAddress
//            chainId = ByteString.copyFrom(BigInteger.valueOf(1002).toByteArray())
//            nonce = ByteString.copyFrom(transactionCount.toByteArray())
//            gasPrice = ByteString.copyFrom(mGasPrice.toByteArray())
//            gasLimit = ByteString.copyFrom(mGasLimit.toByteArray())
//            transaction = NewChain.Transaction.newBuilder().apply {
//                transfer = NewChain.Transaction.Transfer.newBuilder().apply {
//                    amount = ByteString.copyFrom(Convert.toWei(value, Convert.Unit.ETHER).toBigInteger().toByteArray())
//                }.build()
//            }.build()
//        }
    }

    override fun sendRawTransaction(coinName: String, transaction: String): Single<String> {
        AssetUtils.checkCoinName(coinName, CoinType.NEWCHAIN)
        return Single.fromCallable {
            val raw = web3j.ethSendRawTransaction(transaction).send()
            raw.transactionHash
        }
    }
}