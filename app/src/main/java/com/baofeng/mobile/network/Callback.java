package com.baofeng.mobile.network;

import com.abooc.common.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public abstract class Callback<T> implements com.squareup.okhttp.Callback {

    //    private final String TAG = getClass().getSimpleName();
    private final String TAG = "TAG";

    private final Gson jsonParser;


    public Callback() {
        jsonParser = new Gson();
    }


    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) {

        Log.anchor(response);

        if (response.isSuccessful()) {
            String result = null;
            try {
                result = response.body().string();
            } catch (SocketException e) {
                android.util.Log.e(TAG, e.toString());
                return;
            } catch (IOException e) {
                android.util.Log.e(TAG, e.toString());
                onFailure(response.request(), new IOException(response.code() + ", " + response.request().url() + " " + response.message()));
                return;
            }
            Log.anchor(result);

            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

            try {
                T t = jsonParser.fromJson(result, params[0]);
                onResponse(response, t);
            } catch (JsonSyntaxException e) {
                android.util.Log.e(TAG, e.toString());
                onFailure(response.request(), new IOException(response.code() + ", " + response.request().url() + " " + response.message()));
                return;
            }
        } else {
            onFailure(response.request(), new IOException(response.code() + ", " + response.request().url() + " " + response.message()));
        }
    }

    public abstract void onResponse(Response response, T t);

}
