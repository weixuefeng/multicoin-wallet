package com.explorer.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/23/21--2:40 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public class RequestInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
