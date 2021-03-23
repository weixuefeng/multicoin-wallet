package com.explorer.wallettest.api
import com.explorer.network.base.INetworkRequiredInfo
import com.explorer.wallettest.BuildConfig

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--3:22 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class ApiExplorerNetwork: INetworkRequiredInfo {

    override fun getAppVersionName(): String = BuildConfig.VERSION_NAME

    override fun getAppVersionCode(): String = "" + BuildConfig.VERSION_CODE

    override fun isDebug(): Boolean = BuildConfig.DEBUG
}