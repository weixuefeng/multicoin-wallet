package com.explorer.wallettest.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:17 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
open class BaseViewHolder<T>(private val iCustomView: ICustomView<T>)
    : RecyclerView.ViewHolder(iCustomView as View) {

    fun bind(data: T) {
        iCustomView.setData(data)
    }
}
