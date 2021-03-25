package com.explorer.wallettest.ui.base

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:23 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
interface ICustomView<T> {

    fun setData(data: T)

    // set item view listener
    fun setActionListener(listener: ICustomViewActionListener<T>)

}