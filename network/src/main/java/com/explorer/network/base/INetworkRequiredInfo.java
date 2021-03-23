package com.explorer.network.base;

public interface INetworkRequiredInfo {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();
    // 如果需要的话，给该库提供 application。
    // Application getApplicationContext();
}
