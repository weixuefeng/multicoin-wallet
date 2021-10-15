package com.explorer.wallettest.ui.wallet.transfer

import androidx.lifecycle.*
import com.explorer.wallettest.entity.GasSetting
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.repository.IWalletRepository
import com.explorer.wallettest.repository.WalletRepository
import com.explorer.wallettest.ui.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.web3j.protocol.core.methods.request.Transaction
import wallet.core.jni.CoinType
import wallet.core.jni.proto.NewChain

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--3:53 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class TransferViewModel(
    val walletRepository: IWalletRepository
): BaseViewModel() {

    override fun clear() {

    }


    private val gasSetting = MutableLiveData<GasSetting>()

    fun onGasSetting(): LiveData<GasSetting> = gasSetting

    fun getGasSetting(coinName: String, from: String, to: String? = null, data: String? = null) {
        val transaction = Transaction.createEthCallTransaction(from, to, data)
        val subscribe = Single
            .zip(walletRepository.getGasPrice(coinName),
                    walletRepository.estimateGas(coinName, transaction),
                walletRepository.getTransactionCount(coinName, from)
            ) { gasPrice, gasLimit, count -> GasSetting(gasPrice, gasLimit, count) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GasSetting> {
                override fun onSubscribe(d: Disposable) {
                    Logger.d("onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Logger.e("error:" + e.message )
                }

                override fun onSuccess(t: GasSetting) {
                    Logger.d("success: $t")
                    gasSetting.postValue(t)
                }
            })
    }

    fun getTransactionCount(coinType: CoinType, address: String) = viewModelScope.launch {
        async {
            walletRepository.getTransactionCount(coinType.name, address)
        }
    }

    fun sendTransaction(coinType: CoinType, rawTransaction: String) = walletRepository.sendRawTransaction(coinType.name, rawTransaction)



}

object TransferViewModelFactory : ViewModelProvider.Factory {

    private val walletRepository = WalletRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TransferViewModel(walletRepository) as T
    }
}