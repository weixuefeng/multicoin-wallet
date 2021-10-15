package com.explorer.wallettest.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.explorer.wallettest.App
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.entity.UnWrapAccount
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.utils.gson
import com.explorer.wallettest.utils.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wallet.core.jni.Account

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--1:08 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val CURRENT_WALLET_ID = stringPreferencesKey("current_wallet_id")
val CURRENT_ACCOUNT = stringPreferencesKey("current_account")
val CURRENT_LOCAL_STORE_KEY = stringPreferencesKey("current_local_store_key")

class PreferenceRepository(private val context: Context) {

    fun getCurrentWalletId(): Flow<String?> {
        return context.dataStore.data.map {
            it[CURRENT_WALLET_ID]
        }
    }

    suspend fun setCurrentWalletId(walletId: String) {
        context.dataStore.edit {
            it[CURRENT_WALLET_ID] = walletId
        }
    }

    fun getCurrentLocalStoreKey(): Flow<LocalStoreKey?> {
        return context.dataStore.data.map {
            gson.fromJson(it[CURRENT_LOCAL_STORE_KEY], LocalStoreKey::class.java)
        }
    }

    suspend fun setCurrentLocalStoreKey(localStoreKey: LocalStoreKey) {
        context.dataStore.edit {
            it[CURRENT_LOCAL_STORE_KEY] = localStoreKey.toJson()
        }
    }

    suspend fun setCurrentAccount(account: UnWrapAccount) {
        context.dataStore.edit {
            Logger.d(account.toJson())
            it[CURRENT_ACCOUNT] = account.toJson()
        }
    }

    fun getCurrentAccount(): Flow<UnWrapAccount?> {
        return context.dataStore.data.map {
            gson.fromJson(it[CURRENT_ACCOUNT], UnWrapAccount::class.java)
        }
    }

    companion object {
        fun getInstance(): PreferenceRepository {
            return PreferenceRepository(App.getApplicationContext())
        }
    }
}