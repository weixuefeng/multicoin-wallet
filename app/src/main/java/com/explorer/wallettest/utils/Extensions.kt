package com.explorer.wallettest.utils

import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.entity.UnWrapAccount
import com.explorer.wallettest.logger.Logger
import com.google.gson.Gson
import com.google.protobuf.ByteString
import org.web3j.utils.Numeric
import wallet.core.jni.Account
import wallet.core.jni.CoinType
import wallet.core.jni.StoredKey
import java.math.BigDecimal
import java.math.BigInteger

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

val gson = Gson()

fun Any.toJson(): String{
    return gson.toJson(this)
}

fun Account.unWrap(): UnWrapAccount{
    return UnWrapAccount(coin(), address(), derivationPath(), extendedPublicKey())
}

fun UnWrapAccount.wrap(): Account {
    return Account(address, coin, derivationPath, extendedPublicKey)
}

fun LocalStoreKey.toStoreKey(): StoredKey {
    return StoredKey.importJSON(keystore.toByteArray())
}

fun Int.toHexByteArray(): ByteString {
    return ByteString.copyFrom(Numeric.toHexStringWithPrefix(BigInteger.valueOf(this.toLong())).toHexByteArray())
}

fun BigInteger.toHexByteArray(): ByteString {
    return ByteString.copyFrom(Numeric.toHexStringWithPrefix(this).toHexByteArray())
}

fun BigDecimal.toHexByteArray(): ByteString {
    return ByteString.copyFrom(Numeric.toHexStringWithPrefix(this.toBigInteger()).toHexByteArray())
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

fun StoredKey.getSupportCoin(coinType: CoinType): Account? {
    val accountCount = accountCount()
    for (i in 0 until accountCount) {
        val res = account(i).coin().name === coinType.name
        if(res) {
            return account(i)
        }
    }
    return null
}