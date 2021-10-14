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

    @Query("SELECT * FROM localStoreKey WHERE type = :type order by timeStamp desc")
    fun getLocalStoreKeyByType(type: String): Flow<List<LocalStoreKey>>

    @Update(entity = LocalStoreKey::class)
    suspend fun updateLocalStoreKey(localStoreKey: LocalStoreKey): Int

    @Delete(entity = LocalStoreKey::class)
    suspend fun deleteLocalStoreKey(localStoreKey: LocalStoreKey): Int

    @Insert(entity = LocalStoreKey::class)
    suspend fun addLocalStoreKey(localStoreKey: LocalStoreKey)
}