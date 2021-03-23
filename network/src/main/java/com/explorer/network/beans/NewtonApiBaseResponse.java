package com.explorer.network.beans;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/20/21--6:14 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
public class NewtonApiBaseResponse {
	@SerializedName("error_code")
	public int errorCode;
	@SerializedName("error_message")
	public String errorMessage;
	@SerializedName("result")
	public String result;

	@Override
	public String toString() {
		return "BaseResponse{" +
				"errorCode=" + errorCode +
				", errorMessage='" + errorMessage + '\'' +
				", result='" + result + '\'' +
				'}';
	}
}
