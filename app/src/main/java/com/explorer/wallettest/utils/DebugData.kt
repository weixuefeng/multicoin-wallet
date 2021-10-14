package com.explorer.wallettest.utils

import com.explorer.wallettest.R
import com.explorer.wallettest.ui.walletmanager.CoinInfo
import wallet.core.jni.CoinType

val password = "1111111".toByteArray()

fun getCoinLists(): ArrayList<CoinInfo> {
    return arrayListOf<CoinInfo>(
        CoinInfo(R.drawable.btc, CoinType.BITCOIN),
        CoinInfo(R.drawable.eth, CoinType.ETHEREUM),
        CoinInfo(R.drawable.doge, CoinType.DOGECOIN),
        CoinInfo(R.drawable.newcoin, CoinType.NEWCHAIN),

        CoinInfo(R.drawable.btc, CoinType.BITCOIN),
        CoinInfo(R.drawable.eth, CoinType.ETHEREUM),
        CoinInfo(R.drawable.doge, CoinType.DOGECOIN),
        CoinInfo(R.drawable.newcoin, CoinType.NEWCHAIN),

        CoinInfo(R.drawable.btc, CoinType.BITCOIN),
        CoinInfo(R.drawable.eth, CoinType.ETHEREUM),
        CoinInfo( R.drawable.doge, CoinType.DOGECOIN),
        CoinInfo( R.drawable.newcoin, CoinType.NEWCHAIN),

        CoinInfo( R.drawable.btc, CoinType.BITCOIN),
        CoinInfo( R.drawable.eth, CoinType.ETHEREUM),
    )
}