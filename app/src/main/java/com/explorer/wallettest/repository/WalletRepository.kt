package com.explorer.wallettest.repository

import com.explorer.wallettest.logger.LogInterceptor
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.service.AssetService
import com.explorer.wallettest.service.NewChainAssetService
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import wallet.core.jni.CoinType
import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--6:10 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletRepository : IWalletRepository {

    override fun getGasPrice(coinName: String): Single<BigInteger> {
        val service = getService(coinName)
        return service.getTransactionGasPrice(coinName)
    }

    override fun estimateGas(coinName: String, transaction: Transaction): Single<BigInteger> {
        return getService(coinName).getTransactionGasLimit(coinName, transaction)
    }

    private val newWeb3j = Web3j.build(
        HttpService(
            "https://devnet.newchain.cloud.diynova.com/",
            OkHttpClient().newBuilder().addInterceptor(LogInterceptor()).build(),
            true
        )
    )

    private val ethWeb3j = Web3j.build(
        HttpService(
            "https://devnet.newchain.cloud.diynova.com/",
            OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build(),
            true
        ),
    )

    private fun getService(coinName: String): NewChainAssetService {
        val tmpService = serviceMap[coinName]
        if(tmpService != null) {
            return tmpService
        }
        val web3j = when(coinName) {
            CoinType.NEWCHAIN.name -> newWeb3j
            else -> ethWeb3j
        }
        val service = NewChainAssetService(web3j)
        serviceMap[coinName] = service
        return service
    }

    private val serviceMap = HashMap<String, NewChainAssetService>()
}