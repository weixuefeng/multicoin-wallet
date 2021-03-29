package com.explorer.wallettest.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import com.explorer.wallettest.R
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseCustomView
import com.explorer.wallettest.ui.base.ICustomViewActionListener
import wallet.core.jni.Account

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:35 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
@SuppressLint("ViewConstructor")
class WalletCoinItemView(context: Context, listener: ICustomViewActionListener<Account>): BaseCustomView<Account>(context) {

    companion object {
        const val ACTION_ROOT_VIEW = "ACTION_ROOT_VIEW"
    }

    init {
        setActionListener(listener)
    }

    override fun bindDataToView(data: Account) {
        rootView.findViewById<TextView>(R.id.nameTextView).text = data.coin().name
        rootView.findViewById<TextView>(R.id.addressTextView).text = data.address()
    }

    override fun getLayoutId(): Int {
        return R.layout.wallet_coin_item
    }
}