package com.explorer.wallettest.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.explorer.wallettest.R
import com.explorer.wallettest.databinding.ActivitySplashBinding
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.utils.toHexByteArray
import kotlinx.android.synthetic.main.activity_splash.*
import wallet.core.jni.proto.NewChain

import com.google.protobuf.ByteString
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.PrivateKey

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--4:51 PM
 * @description Splash Activity, 检查本地是否有钱包，有钱包进入钱包页面，没有钱包进入创建钱包页面
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class SplashActivity: BaseActivity<SplashViewModel>() {

    private val splashViewModel: SplashViewModel by viewModels { SplashViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashDataBinding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setContentView(splashDataBinding.root)
        splashDataBinding.viewModel = splashViewModel
        splashDataBinding.lifecycleOwner = this

        splashViewModel.onCountDownTime().observe(this) {
            secondTextView.text = it
            splashViewModel.onCurrentWalletId().observe(this, Observer { walletId ->
                Logger.d(walletId)
                if(walletId == null) {
                    // no wallet
                    Router.openHomeActivity(this)
                } else {
                    // go to wallet
                    Router.openHomeActivity(this)
                }
                finish()
            })
        }
    }

    override fun initView() {
    }

    override fun initViewModel() {
        viewModel = splashViewModel
    }

    override fun contentViewId(): Int {
        return R.layout.activity_splash
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }

}