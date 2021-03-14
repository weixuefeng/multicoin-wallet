package com.explorer.wallettest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/8/21--4:16 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
@Entity(tableName = "localStoreKey")
data class LocalStoreKey (
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "isMnemonic")
        val isMnemonic: Boolean,
        @ColumnInfo(name = "keystore")
        var keystore: String
)