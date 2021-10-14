package com.explorer.wallettest.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/8/21--4:41 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
@Dao
interface LocalStoreKeyDao {

    @Query("SELECT * FROM localStoreKey WHERE id = :id")
    fun getLocalStoreKeyById(id: String): Flow<LocalStoreKey>

    @Query("SELECT * FROM localStoreKey")
    fun getAllLocalStoreKeys(): Flow<List<LocalStoreKey>>

    @Update(entity = LocalStoreKey::class)
    fun updateLocalStoreKey(localStoreKey: LocalStoreKey): Int

    @Delete(entity = LocalStoreKey::class)
    fun deleteLocalStoreKey(localStoreKey: LocalStoreKey): Int

    @Insert(entity = LocalStoreKey::class)
    fun addLocalStoreKey(localStoreKey: LocalStoreKey)
}