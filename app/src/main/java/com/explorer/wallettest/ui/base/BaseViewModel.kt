package com.explorer.wallettest.ui.base

import androidx.lifecycle.ViewModel

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--5:05 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
open abstract class BaseViewModel: ViewModel() {
    abstract fun clear()
}