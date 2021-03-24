package com.explorer.wallettest.ui.wallet

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.ui.base.BaseViewHolder
import com.explorer.wallettest.ui.base.ICustomViewActionListener
import com.explorer.wallettest.ui.common.WalletCoinItemView
import wallet.core.jni.Account
import java.util.*

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:15 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class WalletAdapter: RecyclerView.Adapter<BaseViewHolder<Account>>() {

    private val datas = ArrayList<Account>()

    fun setData(data: List<Account>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Account> {
        val itemView = WalletCoinItemView(parent.context)
        itemView.setActionListener(object : ICustomViewActionListener<Account> {
            override fun onAction(action: String, view: View, data: Account) {
                Router.openAssetDetail(view.context, data)
            }
        })
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Account>, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}