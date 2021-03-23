package com.explorer.network.beans;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/20/21--6:14 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public class ExplorerResponse <T> {
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public T result;
    @SerializedName("status")
    public int status;

    @Override
    public String toString() {
        return "ExplorerResponse{" +
                "message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
