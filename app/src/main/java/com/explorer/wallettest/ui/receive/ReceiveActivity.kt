package com.explorer.wallettest.ui.receive

import android.graphics.Color
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.explorer.wallettest.R
import com.explorer.wallettest.event.ACCOUNT
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_receive.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import wallet.core.jni.Account

class ReceiveActivity : BaseActivity<ReceiveViewModel>() {

    private val receiveViewModel: ReceiveViewModel by viewModels { ReceiveViewModelFactory }


    override fun contentViewId(): Int {
        return R.layout.activity_receive
    }

    override fun initIntent() {
        super.initIntent()
        LiveDataBus.with<Account>(ACCOUNT).observeStick(this, onAccount)
    }

    override fun initView() {

    }

    override fun initViewModel() {
        viewModel = receiveViewModel
    }

    private val onAccount = Observer<Account> {
        tvTitle.text = it.address()
        GlobalScope.run {
            async {
                val bitmap = QRCodeEncoder.syncEncodeQRCode("${it.coin().name.lowercase()}:${it.address()}", 400, Color.parseColor("#ffffff"))
                ivQrCode.setImageBitmap(bitmap)
            }
        }
    }
}