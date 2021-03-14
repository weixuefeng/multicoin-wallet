package com.explorer.wallettest.repository

import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.database.LocalStoreKeyDao

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/11/21--11:20 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class StoreKeyRepository constructor(private val localStoreKeyDao: LocalStoreKeyDao) {

    fun getAllLocalStoreKey() = localStoreKeyDao.getAllLocalStoreKeys()

    fun getLocalStoreKeyById(id: String) = localStoreKeyDao.getLocalStoreKeyById(id)

    suspend fun addLocalStoreKey(localStoreKey: LocalStoreKey) = localStoreKeyDao.addLocalStoreKey(localStoreKey)

    suspend fun updateLocalStoreKey(localStoreKey: LocalStoreKey) = localStoreKeyDao.updateLocalStoreKey(localStoreKey)

    suspend fun deleteLocalStoreKey(localStoreKey: LocalStoreKey) = localStoreKeyDao.deleteLocalStoreKey(localStoreKey)
}