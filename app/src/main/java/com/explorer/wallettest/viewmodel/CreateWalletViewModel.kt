package com.explorer.wallettest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.database.LocalStoreKeyDB
import com.explorer.wallettest.repository.StoreKeyRepository
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
class CreateWalletViewModel(
        private val storeKeyRepository: StoreKeyRepository
) : ViewModel() {

    val localStoreKeys = storeKeyRepository.getAllLocalStoreKey().map { unWrapStoreKeys(it) }
            .asLiveData()

    fun getLocalStoreKeyById(id: String) = storeKeyRepository.getLocalStoreKeyById(id).asLiveData()

    private fun wrapStoreKey(storedKey: StoredKey): LocalStoreKey {
        return LocalStoreKey(storedKey.identifier(), storedKey.isMnemonic, String(storedKey.exportJSON()))
    }

    private fun unWrapStoreKey(localStoreKey: LocalStoreKey): StoredKey {
        return StoredKey.importJSON(localStoreKey.keystore.toByteArray())
    }

    private fun unWrapStoreKeys(localStoreKeys: List<LocalStoreKey>): MutableList<StoredKey> {
        val keys = mutableListOf<StoredKey>()
        for (i in localStoreKeys) {
            keys.add(unWrapStoreKey(i))
        }
        return keys
    }

    fun addLocalStoreKey(storedKey: StoredKey) = viewModelScope.launch {
        val localStoreKey = wrapStoreKey(storedKey)
        storeKeyRepository.addLocalStoreKey(localStoreKey)
    }

    fun updateLocalStoreKey(storedKey: StoredKey) = viewModelScope.launch {
        val localStoreKey = wrapStoreKey(storedKey)
        storeKeyRepository.updateLocalStoreKey(localStoreKey)
    }

    fun deleteLocalStoreKey(storedKey: StoredKey) = viewModelScope.launch {
        storeKeyRepository.deleteLocalStoreKey(wrapStoreKey(storedKey))
    }
}

object CreateWalletViewModelFactory : ViewModelProvider.Factory {

    private val storeKeyRepository = StoreKeyRepository(LocalStoreKeyDB.getInstance().localStoreKeyDao())

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CreateWalletViewModel(storeKeyRepository) as T
    }
}