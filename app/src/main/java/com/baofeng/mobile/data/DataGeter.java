package com.baofeng.mobile.data;

import android.text.TextUtils;

import com.baofeng.mobile.android.AppContext;
import com.baofeng.mobile.bean.Category;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据源
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-4-5.
 */
public class DataGeter {


    static String fixUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("//")) {
            url = "http:" + url;
        } else if (url.startsWith("www.")) {
            url = "http://" + url;
        }
        return url;
    }

    public static String loadJsonData(String filepath) {
        try {
            InputStream inputStream = AppContext.getContext().getAssets().open(filepath);
            return FileUtils.readInStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Category> getList(int size) {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double random = Math.random();
            int count = (int) (random * 10000);
            Category category = new Category(i + "",
                    "类别" + i,
                    "",
                    count);
            list.add(category);
        }
        return list;
    }

}
