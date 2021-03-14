package com.explorer.wallettest

import org.junit.Test
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 2/23/21--11:34 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletTest {
    init {
        System.loadLibrary("TrustWalletCore");
    }

    @Test
    fun test() {
        val mnemonic = "minimum sudden skirt insect reward giant neither apology spell lecture text supply"
        val hdWallet = HDWallet(mnemonic, "")
        val address = hdWallet.getAddressForCoin(CoinType.ETHEREUM)
        println(address)
    }
}