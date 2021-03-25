package com.explorer.wallettest.ui.common

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
class WalletCoinItemView(context: Context): BaseCustomView<Account>(context) {

    private val listener: ICustomViewActionListener<Account> = object : ICustomViewActionListener<Account> {
        override fun onAction(action: String, view: View, data: Account) {
            when(action) {
                ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED  -> {
                    Router.openAssetDetail(view.context, data)
                }
            }
        }
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