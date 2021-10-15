package com.explorer.wallettest.ui.scan

import android.os.Vibrator
import androidx.activity.viewModels
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.explorer.wallettest.R
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scan.*


class  ScanActivity : QRCodeView.Delegate, BaseActivity<ScanViewModel>() {

    private val scanViewModel: ScanViewModel by viewModels { ScanViewModelFactory }

    override fun contentViewId(): Int {
        return R.layout.activity_scan
    }

    override fun initView() {
        zxingview.setDelegate(this)
        zxingview.startCamera()
        zxingview.startSpotAndShowRect()
    }

    override fun onStop() {
        zxingview.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        zxingview.onDestroy()
        super.onDestroy()
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun initViewModel() {
        viewModel = scanViewModel
    }

    override fun onScanQRCodeSuccess(result: String?) {
        Logger.d(result)
        vibrate()
        zxingview.startSpot()
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {

    }

    override fun onScanQRCodeOpenCameraError() {

    }
}