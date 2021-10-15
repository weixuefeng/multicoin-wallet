package com.explorer.wallettest.ui.wallet.asset

import androidx.activity.viewModels
import com.explorer.wallettest.R
import com.explorer.wallettest.event.ASSET_DETAIL
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_asset_detail.*
import wallet.core.jni.Account

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/25/21--12:18 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class AssetDetailActivity: BaseActivity<WalletViewModel>() {

    private val assetDetailViewModel: WalletViewModel by viewModels { WalletViewModelFactory }

    lateinit var mAccount: Account

    override fun contentViewId(): Int = R.layout.activity_asset_detail

    override fun initView() { }

    override fun initViewModel() {
        viewModel = assetDetailViewModel
    }

    override fun initIntent() {
        LiveDataBus.with<Account>(ASSET_DETAIL).observeStick(this, {
            mAccount = it
            addressTextView.text = mAccount.address()
            sendButton.setOnClickListener {
//                Router.openTransferAssetActivity(this, mAccount)
            }
        })
    }
}