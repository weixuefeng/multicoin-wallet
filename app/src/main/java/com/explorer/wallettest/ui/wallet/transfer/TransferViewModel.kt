package com.explorer.wallettest.ui.wallet.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.explorer.wallettest.database.LocalStoreKeyDB
import com.explorer.wallettest.entity.GasSetting
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.repository.IWalletRepository
import com.explorer.wallettest.repository.PreferenceRepository
import com.explorer.wallettest.repository.StoreKeyRepository
import com.explorer.wallettest.repository.WalletRepository
import com.explorer.wallettest.ui.base.BaseViewModel
import com.explorer.wallettest.utils.RxUD
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

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
        Logger.d("getGasSetting")
        val transaction = Transaction.createEthCallTransaction(from, to, data)
        val subscribe = Single
            .zip(walletRepository.getGasPrice(coinName),
                    walletRepository.estimateGas(coinName, transaction),
                    { t1, t2 -> GasSetting(t1, t2) })
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


}

object TransferViewModelFactory : ViewModelProvider.Factory {

    private val walletRepository = WalletRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TransferViewModel(walletRepository) as T
    }
}