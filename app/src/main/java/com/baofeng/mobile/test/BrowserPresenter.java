package com.baofeng.mobile.test;

import com.abooc.common.Log;
import com.baofeng.mobile.data.DataGeter;
import com.baofeng.mobile.network.Callback;
import com.baofeng.mobile.network.OkHttpUtil;
import com.baofeng.mobile.network2.ApiClient;
import com.baofeng.mobile.network2.JsonResponseHandler;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Random;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public class BrowserPresenter {

    //    String url = "http://httpbin.org/delay/3";
//    String url = "https://api.github.com/gists/";
//    String url = "https://api.github.com/gists/c2a7c39532239ff261be";
    public static String url = "http://image.baidu.com/detail/info?fr=channel" +
            "&tag1=%E6%91%84%E5%BD%B1" +
            "&tag2=%E9%A3%8E%E6%99%AF" +
            "&column=%E6%91%84%E5%BD%B1" +
            "&tag=%E9%A3%8E%E6%99%AF" +
            "&ie=utf-8" +
            "&oe=utf-8" +
            "&word=1&t=1437561473602";


    private BrowserFragment mViewer;
    private Random random = new Random();

    public void onTakeView(BrowserFragment viewer) {
        this.mViewer = viewer;
    }

    public void load(int page, int pageSize) {
        if (page > 0) {
            mLoading = true;
            mViewer.onLoading();

            loadFromLocal(page);
        } else {
            mLoading = true;
            mViewer.onLoading();

            loadFromRemote(url, ICallback);
        }
    }

    private boolean mLoading;

    public boolean isLoading() {
        return mLoading;
    }

    private void loadFromLocal(final int page) {
        int i = random.nextInt(5) + 3;
        mViewer.getUIHandler().postDelayed(iRunnable, i * 100);
    }

    private Runnable iRunnable = new Runnable() {
        @Override
        public void run() {

            if(random.nextBoolean()){
                onError("网络链接错误！");
                mViewer.getUIHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoading = false;
                    }
                }, 1000);
                return;
            }

            String json = DataGeter.loadJsonData("default/test_data.json");
            JsonResult jsonResult = new Gson().fromJson(json, JsonResult.class);
            onOK(jsonResult);
            mLoading = false;
        }
    };

    void loadFromRemote(String url, Callback callback) {
//        Request request = new Request.Builder().url(url)
////                .cacheControl(new CacheControl.Builder()
////                        .maxStale(1, TimeUnit.DAYS).build())
//                .tag(this)
//                .build();
//        OkHttpUtil.enqueue(request, callback);


        ApiClient.get(url, JsonHandler);
    }

    public void cancel(){
        OkHttpUtil.cancel(this);
        ApiClient.cancel();
    }

    private JsonResponseHandler<JsonResult> JsonHandler = new JsonResponseHandler<JsonResult>() {

        @Override
        public void onFinish() {
            mLoading = false;
        }

        @Override
        public void onSuccess(int statusCode, JsonResult obj) {
            onOK(obj);
        }

        @Override
        public void onFailure(int statusCode, String error, String rawJsonData) {
            onError(error);
        }
    };

    private Callback<JsonResult> ICallback = new Callback<JsonResult>() {

        @Override
        public void onFailure(Request request, IOException e) {
            mLoading = false;
            Log.anchor(e);
            onError(e.toString());
        }

        @Override
        public void onResponse(Response response, JsonResult jsonResult) {
            mLoading = false;
            onOK(jsonResult);
        }
    };

    private void onError(String error) {
        mViewer.onError(error);
    }

    private void onOK(JsonResult jsonResult) {
        mViewer.hideEmptyView();
        mViewer.onAttachData(jsonResult.data.thumbs);

    }
}