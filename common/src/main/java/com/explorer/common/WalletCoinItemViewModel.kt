package com.explorer.common

import com.explorer.base.customview.BaseCustomViewModel

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--5:33 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
data class WalletCoinItemViewModel(
    val url: String,
    val name: String,
    val balance: String
): BaseCustomViewModel()