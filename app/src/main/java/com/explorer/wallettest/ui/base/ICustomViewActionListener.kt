package com.explorer.wallettest.ui.base

import android.view.View

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/24/21--6:23 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
interface ICustomViewActionListener<T> {

    fun onAction(action: String = ACTION_ROOT_VIEW_CLICKED, view: View, data: T)

    companion object {
        const val ACTION_ROOT_VIEW_CLICKED = "action_root_view_clicked"
    }
}