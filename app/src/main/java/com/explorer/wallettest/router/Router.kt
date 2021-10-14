package com.explorer.wallettest.router

import android.app.Activity
import android.content.Intent
import com.explorer.wallettest.event.ASSET_DETAIL
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.ui.home.HomeActivity
import com.explorer.wallettest.ui.wallet.CreateWalletActivity
import com.explorer.wallettest.ui.wallet.CurrentWalletActivity
import com.explorer.wallettest.ui.wallet.WalletListActivity
import com.explorer.wallettest.ui.wallet.asset.AssetDetailActivity
import com.explorer.wallettest.ui.wallet.transfer.TransferAssetActivity
import wallet.core.jni.Account

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--12:33 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
object Router {

    fun openWalletListActivity(context: Activity) {
        val intent = Intent(context, WalletListActivity::class.java)
        context.startActivity(intent)
    }

    fun openCreateWalletActivity(context: Activity) {
        val intent = Intent(context, CreateWalletActivity::class.java)
        context.startActivity(intent)
    }

    fun openCurrentWalletActivity(context: Activity) {
        val intent = Intent(context, CurrentWalletActivity::class.java)
        context.startActivity(intent)
    }

    fun openHomeActivity(context: Activity) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    fun openAssetDetail(context: Activity, data: Account) {
        val intent = Intent(context, AssetDetailActivity::class.java)
        context.startActivity(intent)
        LiveDataBus.with<Account>(ASSET_DETAIL).postStickData(data)
    }

    fun openTransferAssetActivity(context: Activity, mAccount: Account) {
        val intent = Intent(context, TransferAssetActivity::class.java)
        context.startActivity(intent)
        LiveDataBus.with<Account>(ASSET_DETAIL).postStickData(mAccount)
    }

}