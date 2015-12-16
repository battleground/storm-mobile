package com.baofeng.mobile.network2;

import android.content.res.Resources;
import android.text.TextUtils;

import com.baofeng.mobile.android.AppContext;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyStore;

/**
 * @author liruiyu allnet@live.cn
 */
public class ApiClient {
    static AsyncHttpClient client = new AsyncHttpClient();
	
	private static final String APP_VERSION = "1.0";//API的版本号
	private static final String DEVICE_ANDROID = "2";//android
	
    class Params {
        public static final String APP_TOKEN = "apptoken";
        public static final String AUTH_TOKEN = "token";
        public static final String METHOD = "method";
        public static final String EXTEND = "extend";
        public static final String APP_VERSION = "appversion";//API的版本号
        public static final String PLATFORM = "requestplatform";//API的版本号
    }

    public static class Api {
        private static final Resources mResources = AppContext.getContext().getResources();
        static final String SERVER = "https://api.aituzi.com/";
        private static final String APP_TOKEN = "2287502e830700edd2a2829b8e7c1404";

        public static String getApi(int resId) {
            return mResources.getString(resId);
        }

        public static String getParams(int resId) {
            String[] s = mResources.getStringArray(resId);
            return toString(s);
        }

        private static String toString(Object[] array) {
            if (array == null || array.length == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder(array.length * 7);
            sb.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                sb.append(",");
                sb.append(array[i]);
            }
            return sb.toString();
        }


    }

    ApiClient() {
    }

    static{
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(socketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsyncHttpClient getHttpClient(){
        return client;
    }

    RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put(Params.APP_TOKEN, Api.APP_TOKEN);
        params.put("time", System.currentTimeMillis());
        params.put(Params.APP_VERSION, APP_VERSION);
        params.put(Params.PLATFORM, DEVICE_ANDROID);
        return params;
    }

    public static void get(String url, JsonResponseHandler handlerInterface){
        client.get(url, handlerInterface);
    }

    void post(RequestParams params, ResponseHandlerInterface httpHandler) {
        post(params, httpHandler, false);
    }

    void post(RequestParams params, ResponseHandlerInterface httpHandler, boolean blockCahce) {
        if (blockCahce)
            params.put("time", System.currentTimeMillis());
//        String method = params.getUrlParams().get(Params.METHOD);
//        Log.anchor(method + "\n" + params.toString().replace("&", "\n"));
        client.post(Api.SERVER, params, httpHandler);
    }

    public static void cancel(){
        client.cancelAllRequests(true);
    }

    /**
     * 如果value为空，则不添加该参数
     *
     * @param params
     * @param key
     * @param value
     * @return true参数添加成功，false参数未添加
     */
    boolean putNotNull(RequestParams params, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            params.put(key, value);
            return true;
        }
        return false;
    }

    /**
     * put文件
     *
     * @param params
     * @param key
     * @param filepath
     * @return
     */
    boolean putFile(RequestParams params, String key, String filepath) {
        if (TextUtils.isEmpty(filepath)) return false;
        try {
            params.put(key, new File(filepath));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
