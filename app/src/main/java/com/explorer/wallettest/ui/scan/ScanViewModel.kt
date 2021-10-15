package com.explorer.wallettest.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.explorer.wallettest.ui.base.BaseViewModel

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--3:53 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class ScanViewModel: BaseViewModel() {

    override fun clear() {

    }


}

object ScanViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ScanViewModel() as T
    }
}