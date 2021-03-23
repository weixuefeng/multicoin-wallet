package com.explorer.wallettest.api

import com.explorer.network.beans.ExplorerResponse
import com.explorer.wallettest.entity.TokenListData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--2:44 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public interface ApiNewExplorer {

    // Get list of tokens owned by address.
    // ?module=account&action=tokenlist&address={addressHash}
    @GET("api/")
    fun getTokensListByAddress(@Query("address") addressHash: String, @Query("module") module: String = "account", @Query("action") action: String = "tokenlist"): Observable<ExplorerResponse<List<TokenListData>>>
}