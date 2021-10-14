package com.explorer.wallettest.ui.walletmanager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explorer.wallettest.R
import com.explorer.wallettest.utils.getCoinLists
import kotlinx.android.synthetic.main.item_coin.view.*
import wallet.core.jni.CoinType

data class CoinInfo(
    val icon:  Int,
    val coinType: CoinType,
    var selected: Boolean = false
)

interface OnItemClickListener<T> {
    fun onClick(position: Int, value: T)
}

class CoinRecyclerAdapter(): RecyclerView.Adapter<CoinRecyclerViewHolder>() {

    private val coinList = getCoinLists()

    private var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
        return CoinRecyclerViewHolder(itemView).also {
            it.setItemClickListener(mClickListener!!)
        }
    }

    override fun onBindViewHolder(holder: CoinRecyclerViewHolder, position: Int) {
        holder.bind(position, coinList[position])
    }

    fun getSelectedCoinInfo(): CoinInfo {
        return coinList[selectedIndex]
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    var mClickListener: OnItemClickListener<CoinInfo>? = null

    fun setItemClickListener(clickListener: OnItemClickListener<CoinInfo>) {
        mClickListener = clickListener
    }

    fun updateInfo(position: Int) {
        coinList[position].selected = true
        coinList[selectedIndex].selected = false
        notifyItemChanged(position)
        notifyItemChanged(selectedIndex)
        selectedIndex = position
    }

    companion object {
        val TAG = "Adapter"
    }
}

class CoinRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mClickListener: OnItemClickListener<CoinInfo>? = null

    fun setItemClickListener(clickListener: OnItemClickListener<CoinInfo>) {
        mClickListener = clickListener
    }

    fun bind(position: Int, data: CoinInfo) {
        itemView.image.setImageResource(data.icon)
        if(data.selected) {
            itemView.rootView.setBackgroundColor(Color.BLUE)
        } else {
            itemView.rootView.setBackgroundColor(Color.WHITE)
        }

        mClickListener?.let {
            itemView.rootView.setOnClickListener {
                mClickListener!!.onClick(position, data)
            }
        }
    }

    companion object {
        val TAG = "CoinRecyclerViewHolder"
    }
}