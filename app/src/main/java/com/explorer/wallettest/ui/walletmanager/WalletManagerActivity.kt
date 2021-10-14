package com.explorer.wallettest.ui.walletmanager

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.explorer.wallettest.R
import com.explorer.wallettest.constants.WALLET_MAIN
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.utils.password
import com.explorer.wallettest.utils.supportCoin
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_wallet_manager.*
import wallet.core.jni.StoredKey

class WalletManagerActivity : BaseActivity<WalletViewModel>() {

    private val walletViewModel: WalletViewModel by viewModels { WalletViewModelFactory }

    lateinit var mCoinInfo: CoinInfo
    private var mCurrentStoreKey: StoredKey? = null

    override fun initView() {
        coinRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CoinRecyclerAdapter()

        adapter.setItemClickListener(object : OnItemClickListener<CoinInfo> {
            override fun onClick(position: Int, value: CoinInfo) {
                adapter.updateInfo(position)
                chainName.text = value.coinType.name
                mCoinInfo = value
            }
        })

        coinRecyclerView.adapter = adapter
        mCoinInfo = adapter.getSelectedCoinInfo()
        chainName.text = mCoinInfo.coinType.name
        createWalletButton.setOnClickListener {
            createWallet()
        }
    }

    private fun createWallet() {
        // check has current account ? if exist, use new storeKey, else use main wallet store key
        if(mCurrentStoreKey == null) {
            val storeKey = StoredKey(mCoinInfo.coinType.name + System.currentTimeMillis(), password)
            val hdWallet = storeKey.wallet(password)
            storeKey.accountForCoin(mCoinInfo.coinType, hdWallet)
            viewModel.addLocalStoreKey(storeKey, WALLET_MAIN)
        } else {
            mCurrentStoreKey!!.supportCoin(mCoinInfo.coinType).let { hasSupport ->
                if(hasSupport) {
                    val storeKey = StoredKey(mCoinInfo.coinType.name + System.currentTimeMillis(), password)
                    val hdWallet = storeKey.wallet(password)
                    storeKey.accountForCoin(mCoinInfo.coinType, hdWallet)
                    viewModel.addLocalStoreKey(storeKey, mCoinInfo.coinType.name)
                } else {
                    val hdWallet = mCurrentStoreKey!!.wallet(password)
                    mCurrentStoreKey!!.accountForCoin(mCoinInfo.coinType, hdWallet)
                    viewModel.updateLocalStoreKey(mCurrentStoreKey!!)
                }
            }
        }
    }

    override fun contentViewId(): Int = R.layout.activity_wallet_manager

    override fun initViewModel() {
        viewModel = walletViewModel
        viewModel.getMainStoreKey().observe(this, onMainStoreKey)
    }

    private val onMainStoreKey = Observer<List<LocalStoreKey>> {
        Logger.d(TAG, "main store key length: ${it.size}")
        if (it.isEmpty()) {
            return@Observer
        } else {
            val localStoreKey = it[0]
            mCurrentStoreKey = viewModel.unWrapStoreKey(localStoreKey)
        }
    }

}