package com.explorer.wallettest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.explorer.wallettest.App
import com.explorer.wallettest.constants.LocalStoreKeyName

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/8/21--5:06 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
@Database(entities = [LocalStoreKey::class], version = 1, exportSchema = false)
abstract class LocalStoreKeyDB: RoomDatabase() {

    abstract fun localStoreKeyDao(): LocalStoreKeyDao

    companion object {

        @Volatile private var INSTANCE: LocalStoreKeyDB? = null

        fun getInstance(): LocalStoreKeyDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(App.getApplicationContext()).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): LocalStoreKeyDB =
            Room.databaseBuilder(context.applicationContext,
                LocalStoreKeyDB::class.java, LocalStoreKeyName)
                .build()
    }
}