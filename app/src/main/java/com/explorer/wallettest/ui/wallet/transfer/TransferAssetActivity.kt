package com.explorer.wallettest.ui.wallet.transfer

import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.explorer.wallettest.R
import com.explorer.wallettest.entity.GasSetting
import com.explorer.wallettest.event.ASSET_DETAIL
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_transfer_asset.*
import wallet.core.jni.Account

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/25/21--11:40 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class TransferAssetActivity: BaseActivity<TransferViewModel>() {

    override fun contentViewId(): Int = R.layout.activity_transfer_asset

    private val transferViewModel: TransferViewModel by viewModels { TransferViewModelFactory }

    override fun initView() {

    }

    override fun initIntent() {
        LiveDataBus.with<Account>(ASSET_DETAIL).observeStick(this, onAccount)
    }

    override fun initViewModel() {
        viewModel = transferViewModel
        viewModel.onGasSetting().observe(this, onGasSetting)
    }

    private lateinit var mAccount: Account

    private val onGasButtonClick = View.OnClickListener {
        val toAddress = addressInput.text.toString()
        val value = valueInput.text.toString()
        // todo: check to address and value
        // todo: check asset, if erc20, get gas price. else others.
        viewModel.getGasSetting(mAccount.coin().name, mAccount.address(), toAddress)
    }

    private val onGasSetting = Observer<GasSetting> {
    }

    private val onAccount = Observer<Account> {
        mAccount = it
        findViewById<Button>(R.id.getGasButton).setOnClickListener (onGasButtonClick)
    }
}