package com.explorer.common

import android.content.Context
import android.view.View
import com.explorer.base.customview.BaseCustomView

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--5:27 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletCoinItemView(context:Context): BaseCustomView<WalletCoinItemViewModel>(context) {

    override fun getViewLayoutId(): Int = R.layout.item_wallet_coin


    override fun onDataUpdated() {
    }

    override fun setDataToView(data: WalletCoinItemViewModel) {

    }

    override fun onRootClick(view: View?) {
    }


}