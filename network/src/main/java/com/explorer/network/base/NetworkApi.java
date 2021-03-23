package com.explorer.network.base;


import com.explorer.network.environment.IEnvironment;
import com.explorer.network.errorhandler.*;
import com.explorer.network.interceptor.RequestInterceptor;
import com.explorer.network.interceptor.ResponseInterceptor;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/20/21--12:03 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public abstract class NetworkApi implements IEnvironment {

	protected String mBaseUrl;
	private OkHttpClient mOkHttpClient;
	private static INetworkRequiredInfo mNetworkRequireInfo;
	private final HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

	protected NetworkApi() {
		mBaseUrl = getBaseUrl();
	}


	public static void init(INetworkRequiredInfo networkRequiredInfo) {
		mNetworkRequireInfo = networkRequiredInfo;
	}

	public Retrofit getRetrofit(Class service) {
		String retrofitKey = mBaseUrl + service.getName();
		if (retrofitHashMap.get(retrofitKey) != null) {
			return retrofitHashMap.get(retrofitKey);
		}
		Retrofit.Builder builder = new Retrofit.Builder();
		builder.baseUrl(mBaseUrl);
		builder.addConverterFactory(GsonConverterFactory.create());
		builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
		builder.client(getOkHttpClient());
		Retrofit retrofit = builder.build();
		retrofitHashMap.put(retrofitKey, retrofit);
		return retrofit;
	}

	private OkHttpClient getOkHttpClient() {
		if (mOkHttpClient != null) {
			return mOkHttpClient;
		}
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
		if(mNetworkRequireInfo != null && mNetworkRequireInfo.isDebug()) {
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			builder.addInterceptor(httpLoggingInterceptor);
		}
		Interceptor extraInterceptor = getInterceptor();
		if (extraInterceptor != null) {
			builder.addInterceptor(extraInterceptor);
		}
		builder.addInterceptor(new RequestInterceptor());
		builder.addInterceptor(new ResponseInterceptor());
		mOkHttpClient = builder.build();
		return mOkHttpClient;
	}

	public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
		return upstream -> {
			Observable<T> observable = upstream.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread()).map(getAppErrorHandler())
					.onErrorResumeNext(new HttpErrorHandler<T>());
			observable.subscribe(observer);
			return observable;
		};
	}

	protected abstract <T> Function<T, T> getAppErrorHandler();

	protected abstract Interceptor getInterceptor();

}
