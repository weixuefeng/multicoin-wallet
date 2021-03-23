package com.explorer.wallettest

import android.app.Application
import android.content.Context
import com.explorer.network.base.NetworkApi
import com.explorer.wallettest.api.ApiExplorerNetwork

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/8/21--11:33 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */

class App: Application() {

    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate() {
        super.onCreate()
        _applicationContext = applicationContext
        initNetworkApi()
    }

    private fun initNetworkApi() {
        NetworkApi.init(ApiExplorerNetwork())
    }

    companion object {
        var _applicationContext: Context? = null

        fun getApplicationContext(): Context {
            return _applicationContext!!
        }
    }
}