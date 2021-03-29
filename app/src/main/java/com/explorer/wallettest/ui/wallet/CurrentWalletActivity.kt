package com.explorer.wallettest.ui.wallet

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explorer.wallettest.R
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.event.ADD_ASSET
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.ui.base.ICustomViewActionListener
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_current_wallet.*
import wallet.core.jni.Account
import wallet.core.jni.StoredKey
import java.util.*

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--4:30 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class CurrentWalletActivity : BaseActivity<WalletViewModel>() {
    private val walletViewModel: WalletViewModel by viewModels { WalletViewModelFactory }

    override fun initView() {

        viewModel.onCurrentWalletId().observe(this) {
            if(it != null) {
                viewModel.getLocalStoreKeyById(it).observe(this) { localKey ->
                    initWallet(localKey)
                }
            }
        }
    }

    private fun initWallet(localStoreKey: LocalStoreKey) {
        idTextView.text = localStoreKey.id
        val storeKey = viewModel.unWrapStoreKey(localStoreKey)

        addAsset.setOnClickListener {
            Router.openWalletListActivity(this)
            LiveDataBus.with<StoredKey>(ADD_ASSET).postStickData(storeKey)
        }

        val accountCount = storeKey.accountCount()
        val account = ArrayList<Account>()
        for (i in 0 until accountCount) {
            account.add(storeKey.account(i))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = WalletAdapter(itemClickListener)
        recyclerView.adapter = adapter
        adapter.setData(account)
    }

    private val itemClickListener: ICustomViewActionListener<Account> = object : ICustomViewActionListener<Account> {
        override fun onAction(action: String, view: View, data: Account) {
            when(action) {
                ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED -> {
                    Router.openAssetDetail(this@CurrentWalletActivity, data)
                }
            }
        }

    }

    override fun initViewModel() {
        viewModel = walletViewModel
    }

    override fun contentViewId(): Int = R.layout.activity_current_wallet
}