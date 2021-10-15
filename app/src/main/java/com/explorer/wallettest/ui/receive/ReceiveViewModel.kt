package com.explorer.wallettest.ui.receive

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.explorer.wallettest.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/26/21--3:53 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class ReceiveViewModel: BaseViewModel() {

    override fun clear() {

    }

    fun generateQRCode(contents: String) = viewModelScope.launch {
        async {
            QRCodeEncoder.syncEncodeQRCode(contents, 400, Color.parseColor("#ff0000"))
        }
    }

}

object ReceiveViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ReceiveViewModel() as T
    }
}