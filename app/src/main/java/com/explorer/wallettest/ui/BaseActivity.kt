package com.explorer.wallettest.ui

import androidx.appcompat.app.AppCompatActivity

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--12:24 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
open abstract class BaseActivity: AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        initView()
    }

    abstract fun initView()

    companion object {
        val TAG = this::class.java.canonicalName
    }
}