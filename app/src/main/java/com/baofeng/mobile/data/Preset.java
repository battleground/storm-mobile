package com.baofeng.mobile.data;

import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Data;
import com.baofeng.mobile.bean.Status;
import com.baofeng.mobile.bean.VideoRecommend;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 负责读取默认的配置数据
 *
 *
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class Preset {

    /**
     * 读取预置的影片'类型'数据
     * @return 返回类型集合，每一元素包括：id、类型名、图标ResId、影片数量
     */
    public static List<Category> getCategories(){
        String json = DataGeter.loadJsonData("default/categories.json");

        Gson gson = new Gson();
        Data<JsonElement> result = gson.fromJson(json, Data.class);
        Category[] categories = gson.fromJson(gson.toJson(result.data), Category[].class);
        List<Category> list = Arrays.asList(categories);
        return  list;
    }

    /**
     * 读取影库首页数据
     */
    public static List<VideoRecommend> getVideoMian(){
        String json = DataGeter.loadJsonData("default/test_video_main.json");

        Gson gson = new Gson();
        Status result = gson.fromJson(json, Status.class);
        json = gson.toJson(result.getData());
        VideoRecommend[] recommend = gson.fromJson(json, VideoRecommend[].class);
        List<VideoRecommend> list = Arrays.asList(recommend);
        return new ArrayList<>(list);
    }
}
