package com.explorer.wallettest.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explorer.wallettest.R
import com.explorer.wallettest.entity.BTC
import com.explorer.wallettest.entity.ETH
import com.explorer.wallettest.entity.ItemCoinInfo
import com.explorer.wallettest.entity.NEW
import com.explorer.wallettest.event.ADD_ASSET
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_wallet_list.*
import wallet.core.jni.CoinType
import wallet.core.jni.StoredKey

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--12:30 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletListActivity : BaseActivity<WalletViewModel>() {

    private val password = "111111".toByteArray()

    private val supportCoins = hashMapOf<CoinType, ItemCoinInfo>()

    private lateinit var storedKey: StoredKey

    private val walletViewModel: WalletViewModel by viewModels { WalletViewModelFactory }


    init {
        val btc = ItemCoinInfo(CoinType.BITCOIN, BTC)
        val eth = ItemCoinInfo(CoinType.ETHEREUM, ETH)
        val new = ItemCoinInfo(CoinType.NEWCHAIN, NEW)
        supportCoins[CoinType.BITCOIN] = btc
        supportCoins[CoinType.ETHEREUM] = eth
        supportCoins[CoinType.NEWCHAIN] = new
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LiveDataBus.with<StoredKey>(ADD_ASSET).observeStick(this, onStoredKeyObserver)
    }

    private val walletItemListener = object : OnWalletItemListener {

        override fun onCheckedChanged(item: ItemCoinInfo, isChecked: Boolean) {
            if(isChecked) {
                //todo: add password
                val hdWallet = storedKey.wallet(password)
                storedKey.accountForCoin(item.type, hdWallet)
                viewModel.updateLocalStoreKey(storedKey)

            } else {
                storedKey.removeAccountForCoin(item.type)
                viewModel.updateLocalStoreKey(storedKey)
            }
        }

        override fun onItemClick(item: ItemCoinInfo) {
            Logger.d(item)
            // todo: add index
            storedKey.account(1)
        }

    }

    private val adapter = CoinAdapter(walletItemListener)

    override fun initView() {
        val values = supportCoins.values.toMutableList()
        adapter.setData(values)
        coinRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        coinRecyclerView.layoutManager = linearLayoutManager
    }

    private val onStoredKeyObserver = Observer<StoredKey> {
        storedKey = it
        idTextView.text = it.identifier()
        val accounts = it.accountCount()
        for (i in 0 until accounts) {
            val account = it.account(i)
            val coin = account.coin()
            val itemCoinInfo = supportCoins[coin]
            itemCoinInfo!!.isSupport = true
            supportCoins[coin] = itemCoinInfo
        }
        adapter.setData(supportCoins.values.toMutableList())
    }


    class CoinAdapter(val listener: OnWalletItemListener) : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

        var itemCoinInfos: MutableList<ItemCoinInfo> = mutableListOf<ItemCoinInfo>()

        class ViewHolder(view: View, private val listener: OnWalletItemListener) : RecyclerView.ViewHolder(view) {

            fun bind(item: ItemCoinInfo) {
                itemView.findViewById<TextView>(R.id.coinNameTextView).text = (item.name)
                val switchCompat = itemView.findViewById<SwitchCompat>(R.id.switchCompat)
                switchCompat.isChecked = item.isSupport
                switchCompat.setOnCheckedChangeListener { _, isChecked ->
                    listener.onCheckedChanged(item, isChecked)
                }
                itemView.setOnClickListener {
                    listener.onItemClick(item)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val holder = ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_list_coin, parent, false),
                listener
            )
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(itemCoinInfos[position])
        }

        fun setData(data: MutableList<ItemCoinInfo>) {
            itemCoinInfos.clear()
            Logger.d(data.size)
            itemCoinInfos.addAll(data)
            notifyDataSetChanged()
        }

        fun addData(data: ItemCoinInfo) {
            itemCoinInfos.add(data)
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return itemCoinInfos.size
        }
    }

    interface OnWalletItemListener {
        fun onCheckedChanged(item: ItemCoinInfo, isChecked: Boolean)
        fun onItemClick(item: ItemCoinInfo)
    }

    override fun initViewModel() {
        viewModel = walletViewModel
    }

    override fun contentViewId(): Int = R.layout.activity_wallet_list
}