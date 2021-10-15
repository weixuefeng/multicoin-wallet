package com.explorer.wallettest.ui.walletmanager

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.explorer.wallettest.R
import com.explorer.wallettest.constants.WALLET_MAIN
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.ui.base.ICustomViewActionListener
import com.explorer.wallettest.utils.getSupportCoin
import com.explorer.wallettest.utils.password
import com.explorer.wallettest.utils.supportCoin
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_wallet_manager.*
import wallet.core.jni.Account
import wallet.core.jni.StoredKey

class WalletManagerActivity : BaseActivity<WalletViewModel>() {

    private val walletViewModel: WalletViewModel by viewModels { WalletViewModelFactory }

    lateinit var mCoinInfo: CoinInfo
    private var mCurrentStoreKey: StoredKey? = null
    private var mCurrentLocalStoreKey: LocalStoreKey? = null
    lateinit var accountAdapter: AccountRecyclerAdapter

    override fun initView() {
        coinRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CoinRecyclerAdapter()

        adapter.setItemClickListener(object : OnItemClickListener<CoinInfo> {
            override fun onClick(position: Int, value: CoinInfo) {
                adapter.updateInfo(position)
                chainName.text = value.coinType.name
                mCoinInfo = value
                getAccountInfo()
            }
        })

        coinRecyclerView.adapter = adapter
        mCoinInfo = adapter.getSelectedCoinInfo()
        chainName.text = mCoinInfo.coinType.name
        createWalletButton.setOnClickListener {
            createWallet()
        }

        // account recyclerView info
        coinInfoRecyclerView.layoutManager = LinearLayoutManager(this)
        accountAdapter = AccountRecyclerAdapter(object : ICustomViewActionListener<Account> {
            override fun onAction(action: String, view: View, data: Account) {
                when(action) {
                    ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED -> {
                        viewModel.setCurrentAccount(data)
                        viewModel.setCurrentLocalStoreKey(addressStoreKeyMap[data.address()]!!)
                        finish()
                    }
                }
            }
        })
        coinInfoRecyclerView.adapter = accountAdapter
        // todo: first load default wallet.
        getAccountInfo()
    }

    private fun getAccountInfo() {
        viewModel.getAccountInfo(mCoinInfo.coinType).observe(this, onAccountInfo)
    }

    private val addressStoreKeyMap = mutableMapOf<String, LocalStoreKey>()

    private val onAccountInfo = Observer<List<LocalStoreKey>> { listLocalStoreKey ->
        val accounts = listLocalStoreKey.map {
            val account = viewModel.unWrapStoreKey(it).account(0)
            addressStoreKeyMap[account.address()] = it
            account
        }.toMutableList()

        if(mCurrentStoreKey != null) {
            mCurrentStoreKey!!.getSupportCoin(mCoinInfo.coinType).let {
                if(it != null) {
                    accounts.add(0, it)
                    addressStoreKeyMap[it.address()] = mCurrentLocalStoreKey!!
                }
            }
        }
        accountAdapter.setData(accounts)
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
        if (it.isEmpty()) {
            return@Observer
        } else {
            mCurrentLocalStoreKey = it[0]
            mCurrentStoreKey = viewModel.unWrapStoreKey(mCurrentLocalStoreKey!!)
        }
    }

}