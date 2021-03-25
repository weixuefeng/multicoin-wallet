package com.explorer.wallettest.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/12/21--12:24 AM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
open abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId())
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
        initView()
    }

    abstract fun contentViewId(): Int

    abstract fun initView()

    abstract fun initViewModel()


    companion object {
        val TAG = this::class.java.canonicalName
    }
}