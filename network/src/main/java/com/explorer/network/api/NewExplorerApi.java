package com.explorer.network.api;

import com.explorer.network.base.NetworkApi;
import com.explorer.network.beans.*;
import com.explorer.network.errorhandler.*;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/20/21--12:03 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public class NewExplorerApi extends NetworkApi {

	private static volatile NewExplorerApi mInstance;

	public static NewExplorerApi getInstance() {
		if(mInstance == null) {
			synchronized (NewExplorerApi.class) {
				mInstance = new NewExplorerApi();
			}
		}
		return mInstance;
	}

	public static <T> T getService(Class<T> service) {
		return getInstance().getRetrofit(service).create(service);
	}

	@Override
	protected <T> Function<T, T> getAppErrorHandler() {
		return response -> {
			if (response instanceof ExplorerResponse && ((ExplorerResponse) response).status != 1) {
				ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
				exception.code = ((ExplorerResponse) response).status;
				exception.message = ((ExplorerResponse) response).message != null ? ((ExplorerResponse) response).message : "";
				throw exception;
			}
			return response;
		};
	}

	@Override
	protected Interceptor getInterceptor() {
		return null;
	}

	@Override
	public String getBaseUrl() {
		return "http://e.testnet.diynova.com/";
	}
}
