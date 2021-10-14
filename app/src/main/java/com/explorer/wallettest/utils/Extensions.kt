package com.explorer.wallettest.utils

import com.google.protobuf.ByteString
import org.web3j.utils.Numeric
import wallet.core.jni.CoinType
import wallet.core.jni.StoredKey

fun ByteArray.toHex(): String {
    return Numeric.toHexString(this)
}

fun String.toHexBytes(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toHexByteArray(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toByteString(): ByteString {
    return ByteString.copyFrom(this, Charsets.UTF_8)
}

fun String.toHexBytesInByteString(): ByteString {
    return ByteString.copyFrom(this.toHexBytes())
}

fun StoredKey.supportCoin(coinType: CoinType): Boolean {
    val accountCount = accountCount()
    for (i in 0 until accountCount) {
        val res = account(i).coin().name === coinType.name
        if(res) {
            return true
        }
    }
    return false
}