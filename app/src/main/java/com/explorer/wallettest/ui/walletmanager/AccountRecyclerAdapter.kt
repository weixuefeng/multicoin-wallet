package com.explorer.wallettest.ui.walletmanager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explorer.wallettest.ui.base.BaseViewHolder
import com.explorer.wallettest.ui.base.ICustomViewActionListener
import com.explorer.wallettest.ui.common.WalletCoinItemView
import wallet.core.jni.Account

class AccountRecyclerAdapter(private val listener: ICustomViewActionListener<Account>): RecyclerView.Adapter<BaseViewHolder<Account>>() {

    private val datas = ArrayList<Account>()

    fun setData(data: List<Account>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Account> {
        val itemView = WalletCoinItemView(parent.context, listener)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Account>, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}