package com.explorer.wallettest.router

import android.app.Activity
import android.content.Intent
import com.explorer.wallettest.ui.WalletListActivity

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
}