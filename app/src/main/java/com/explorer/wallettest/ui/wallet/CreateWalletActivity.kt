package com.explorer.wallettest.ui.wallet

import  android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explorer.wallettest.R
import com.explorer.wallettest.event.CREATE_WALLET_STORE_KEY
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.viewmodel.WalletViewModel
import com.explorer.wallettest.viewmodel.WalletViewModelFactory
import wallet.core.jni.StoredKey

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/8/21--11:32 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class CreateWalletActivity : BaseActivity<WalletViewModel>() {

    private val password = "111111".toByteArray()

    private val walletViewModel: WalletViewModel by viewModels { WalletViewModelFactory }

    private val listener: OnStoreItemClickListener = object : OnStoreItemClickListener {

        override fun onDeleteItem(storedKey: StoredKey) {
            viewModel.deleteLocalStoreKey(storedKey)
        }

        override fun onItemClick(storedKey: StoredKey) {
            LiveDataBus.with<StoredKey>(CREATE_WALLET_STORE_KEY).postStickData(storedKey)
            Router.openWalletListActivity(this@CreateWalletActivity)
        }
    }

    private val walletAdapter = WalletAdapter(listener)

    override fun initView() {
        val createWalletButton = findViewById<Button>(R.id.createWalletButton)
        createWalletButton.setOnClickListener(createWalletButtonClick)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = walletAdapter
        viewModel.localStoreKeys.observe(this, onLocalStoreKeys)
    }

    override fun initViewModel() {
        viewModel = walletViewModel
    }


    private val onLocalStoreKeys = Observer<MutableList<StoredKey>> {
        walletAdapter.setData(it)
    }

    private val createWalletButtonClick = View.OnClickListener {
        val storeKey = StoredKey("name", password)
        viewModel.addLocalStoreKey(storeKey)
        walletAdapter.addData(storeKey)
    }


    class WalletAdapter(private val listener: OnStoreItemClickListener) :
        RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

        var storeKeys: MutableList<StoredKey> = mutableListOf<StoredKey>()

        class ViewHolder(view: View, private val listener: OnStoreItemClickListener) :
            RecyclerView.ViewHolder(view) {
            fun bind(item: StoredKey) {
                itemView.findViewById<TextView>(R.id.idTextView).text = (item.identifier())
                itemView.findViewById<TextView>(R.id.contentTextView).text =
                    " ${item.accountCount()}"
                itemView.findViewById<Button>(R.id.deleteButton)
                    .setOnClickListener { listener.onDeleteItem(item) }
                itemView.setOnClickListener { listener.onItemClick(item) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val holder = ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false),
                listener
            )
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(storeKeys[position])
        }

        fun setData(data: MutableList<StoredKey>) {
            storeKeys.clear()
            storeKeys.addAll(data)
            notifyDataSetChanged()
        }

        fun addData(data: StoredKey) {
            storeKeys.add(data)
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return storeKeys.size
        }
    }

    interface OnStoreItemClickListener {
        fun onDeleteItem(storedKey: StoredKey)
        fun onItemClick(storedKey: StoredKey)
    }

    override fun contentViewId(): Int = R.layout.activity_create_wallet
}