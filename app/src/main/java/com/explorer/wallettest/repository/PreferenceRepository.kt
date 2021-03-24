package com.explorer.wallettest.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.explorer.wallettest.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--1:08 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val CURRENT_WALLET_ID = stringPreferencesKey("current_wallet_id")

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

    companion object {
        fun getInstance(): PreferenceRepository {
            return PreferenceRepository(App.getApplicationContext())
        }
    }
}