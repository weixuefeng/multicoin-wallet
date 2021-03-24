package com.explorer.wallettest.ui.splash

import androidx.lifecycle.*
import com.explorer.wallettest.database.LocalStoreKeyDB
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.repository.PreferenceRepository
import com.explorer.wallettest.repository.StoreKeyRepository
import com.explorer.wallettest.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--5:06 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class SplashViewModel(
    private val preferenceRepository: PreferenceRepository
): BaseViewModel() {

    private val disposable: Disposable

    init {
        disposable = Observable.interval(1, TimeUnit.SECONDS)
            .map { "$it" }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{next ->
                run {
                    Logger.d(next)
                    countDownTime.postValue(next)
                }
            }
    }

    val countDownTime = MutableLiveData<String>()
    fun onCountDownTime(): LiveData<String> = countDownTime

    fun onCurrentWalletId() = preferenceRepository.getCurrentWalletId().asLiveData()

    override fun clear() {
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}

object SplashViewModelFactory : ViewModelProvider.Factory {
    private val preferenceRepository = PreferenceRepository.getInstance()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SplashViewModel(preferenceRepository) as T
    }
}