package com.baofeng.mobile.network2;

import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

/**
 * Json解析
 *
 * @param <JSON_TYPE>
 */
public abstract class JsonResponseHandler<JSON_TYPE> extends BaseJsonHttpResponseHandler<JSON_TYPE> {

    private Gson mJsonParser = new Gson();

    @Override
    public void onSuccess(int statusCode, Header[] headers, String rawJsonData, JSON_TYPE obj) {
        onSuccess(statusCode, obj);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSON_TYPE o) {
        String error = getErrorMessage(throwable);
        onFailure(statusCode, error, rawJsonData);
    }

    public abstract void onSuccess(int statusCode, JSON_TYPE obj);

    public abstract void onFailure(int statusCode, String error, String rawJsonData);

    @Override
    protected JSON_TYPE parseResponse(String responseString, boolean isFailure) throws Throwable {
        if (!isFailure) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            JSON_TYPE t = mJsonParser.fromJson(responseString, params[0]);
            return t;
        }
        return null;
    }

    private String getErrorMessage(Throwable throwable) {
        if (throwable instanceof ConnectTimeoutException
                || throwable instanceof SocketTimeoutException) {
            return "网络请求超时，请稍后重试！";
        } else if (throwable.getMessage() != null
                && throwable.getMessage().contains("UnknownHostException")) {
            return "请检查网络连接是否可用！";
        } else {
            return "未知错误，稍后再试！";
        }
    }

}
