package com.explorer.wallettest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.explorer.wallettest.constants.WALLET_MAIN

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
        @ColumnInfo(name = "id", defaultValue = "")
        val id: String,

        @ColumnInfo(name = "isMnemonic", defaultValue = "true")
        val isMnemonic: Boolean,

        @ColumnInfo(name = "keystore", defaultValue = "")
        var keystore: String,

        @ColumnInfo(name = "type", defaultValue = WALLET_MAIN)
        val type: String,

        @ColumnInfo(name = "timeStamp", defaultValue = "0")
        val timeStamp: Long

)