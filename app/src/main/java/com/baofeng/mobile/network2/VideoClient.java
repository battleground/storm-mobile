package com.baofeng.mobile.network2;


import com.baofeng.mobile.bean.FilterBean;
import com.baofeng.mobile.bean.Page;
import com.baofeng.mobile.bean.Status;
import com.baofeng.mobile.bean.Video;
import com.loopj.android.http.RequestParams;

import java.util.List;

/**
 * 影库
 */
public class VideoClient extends ApiClient {

    /**
     * 获取指定圈子下的视频列表
     *
     * @param category 分类
     * @param type     筛选 类型
     * @param area     筛选 地区
     * @param year     筛选 年限
     * @param sort     筛选 排序
     * @param page     页数
     * @param pageSize 每页数量
     * @param handler  回调
     */
    public void getVideoList(String category, String sort, String type, String area, String year,
                             int page, int pageSize, final JsonResponseHandler<Status<Page<Video>>> handler, final Object tag) {
        RequestParams params = getRequestParams();
        params.put(Params.METHOD, "me2.video.list");
        params.put("category", category);
        putNotNull(params, "tag", type);
        putNotNull(params, "area", area);
        putNotNull(params, "year", year);
        putNotNull(params, "sort", sort);
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("extend", "name,category,cover,dbscore,isend,unum,resolution");
        post(params, handler);
    }

    /**
     * 获取筛选类型
     *
     * @param httpHandler
     */
    public void getVideoFilter(JsonResponseHandler<Status<List<FilterBean>>> httpHandler) {
        RequestParams params = getRequestParams();
        params.put(Params.METHOD, "me2.video.category");
        params.put("ysort", 2);
        params.put(Params.EXTEND, "tags,areas,years");
        post(params, httpHandler);
    }

}
