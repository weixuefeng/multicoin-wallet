package com.explorer.wallettest.viewmodel

import androidx.lifecycle.*
import com.explorer.wallettest.constants.WALLET_MAIN
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.database.LocalStoreKeyDB
import com.explorer.wallettest.repository.PreferenceRepository
import com.explorer.wallettest.repository.StoreKeyRepository
import com.explorer.wallettest.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import wallet.core.jni.StoredKey

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/9/21--10:32 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletViewModel(
        private val storeKeyRepository: StoreKeyRepository,
        private val preferenceRepository: PreferenceRepository,
) : BaseViewModel() {

    val localStoreKeys = storeKeyRepository.getAllLocalStoreKey().map { unWrapStoreKeys(it) }.asLiveData()

    fun getLocalStoreKeyById(id: String) = storeKeyRepository.getLocalStoreKeyById(id).asLiveData()

    private fun wrapStoreKey(storedKey: StoredKey, type: String = WALLET_MAIN): LocalStoreKey {
        return LocalStoreKey(storedKey.identifier(), storedKey.isMnemonic, String(storedKey.exportJSON()), type, System.currentTimeMillis())
    }

    fun unWrapStoreKey(localStoreKey: LocalStoreKey): StoredKey {
        return StoredKey.importJSON(localStoreKey.keystore.toByteArray())
    }

    private fun unWrapStoreKeys(localStoreKeys: List<LocalStoreKey>): MutableList<StoredKey> {
        val keys = mutableListOf<StoredKey>()
        for (i in localStoreKeys) {
            keys.add(unWrapStoreKey(i))
        }
        return keys
    }

    fun addLocalStoreKey(storedKey: StoredKey, type: String = WALLET_MAIN) = viewModelScope.launch {
        val localStoreKey = wrapStoreKey(storedKey, type)
        async {
            storeKeyRepository.addLocalStoreKey(localStoreKey)
            preferenceRepository.setCurrentWalletId(localStoreKey.id)
        }
    }

    fun updateLocalStoreKey(storedKey: StoredKey) = viewModelScope.launch {
        val localStoreKey = wrapStoreKey(storedKey)
        async {
            storeKeyRepository.updateLocalStoreKey(localStoreKey)
        }
    }

    fun deleteLocalStoreKey(storedKey: StoredKey) = viewModelScope.launch {
        async {
            storeKeyRepository.deleteLocalStoreKey(wrapStoreKey(storedKey))
        }
    }

    fun getMainStoreKey() = storeKeyRepository.getLocalStoreKeyByType(WALLET_MAIN).asLiveData()

    private val currentWallet = MutableLiveData<LocalStoreKey>()

    fun onCurrentWallet(): LiveData<LocalStoreKey> = currentWallet


    fun onCurrentWalletId() = preferenceRepository.getCurrentWalletId().asLiveData()


    override fun clear() {

    }

}

object WalletViewModelFactory : ViewModelProvider.Factory {

    private val storeKeyRepository = StoreKeyRepository(LocalStoreKeyDB.getInstance().localStoreKeyDao())
    private val preferenceRepository = PreferenceRepository.getInstance()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return WalletViewModel(storeKeyRepository, preferenceRepository) as T
    }
}