package com.baofeng.mobile.fragment;

import com.baofeng.mobile.Constant;
import com.baofeng.mobile.R;
import com.baofeng.mobile.bean.Area;
import com.baofeng.mobile.bean.Base;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Page;
import com.baofeng.mobile.bean.Status;
import com.baofeng.mobile.bean.Tag;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.bean.Year;
import com.baofeng.mobile.network2.JsonResponseHandler;
import com.baofeng.mobile.network2.VideoClient;
import com.baofeng.mobile.tools.Snackbar;
import com.baofeng.mobile.tools.ToolUtils;

import java.util.List;

/**
 * 影库列表数据加载
 * @author zhangjunpu
 * @date 15/7/28
 */
public class VideoStoragePresenter {

    private Category category;
    private Base sort; //排序
    private Tag type; //类型
    private Area area; //地区
    private Year year; //年限

    private VideoStorageFragment mViewer;

    public VideoStoragePresenter(VideoStorageFragment mViewer) {
        this.mViewer = mViewer;
        defaultFilter();
    }

    /**
     * 加载数据
     * @param page
     * @param tag
     */
    public void load(int page, final Object tag) {
        VideoClient client = new VideoClient();
        client.getVideoList(category.id, sort.getId(), type.getTag(), area.getArea(), year.getYear(),
                page, Constant.PAGE_SIZE, new JsonResponseHandler<Status<Page<Video>>>() {

            @Override
            public void onFinish() {
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Status<Page<Video>> response) {
                if (mViewer == null || mViewer.isDestroyView()) {
                    return;
                }
                if (response.is200()) {
                    List<Video> list = response.getData().getList();
                    int count = ToolUtils.parseIntSafely(tag);
                    if (list == null || list.isEmpty()) {
                        int total = response.getData().getTotal();
                        if (count >= total) {
                            Snackbar.show(mViewer.getView(), R.string.data_load_all);
                            return;
                        }
                        Snackbar.show(mViewer.getView(), R.string.data_empty);
                        return;
                    }
                    if (tag == null) {
                        update(list);
                    } else {
                        add(list);
                    }
                    Snackbar.show(mViewer.getView(), String.format("加载了%d条", list.size()));
                } else {
                    Snackbar.show(mViewer.getView(), response.getError_msg());
                }
            }

            @Override
            public void onFailure(int statusCode, String error, String rawJsonData) {
                Snackbar.show(mViewer.getView(), error);
            }
        }, tag);
    }

    private void finish() {
        if (mViewer != null) {
            mViewer.onCompleteRefresh();
        }
    }

    private void update(List<Video> list) {
        if (mViewer != null) {
            mViewer.update(list);
        }
    }

    private void add(List<Video> list) {
        if (mViewer != null) {
            mViewer.add(list);
        }
    }

    public void updateFilter(Base sort, Tag type, Area area, Year year) {
        if (this.sort == sort && this.type == type && this.area == area && this.year == year) {
            return;
        }
        this.sort = sort;
        this.type = type;
        this.area = area;
        this.year = year;
        load(1, null);
    }

    public void updateCategory(Category category) {
        this.category = category;
        defaultFilter();
        load(1, null);
    }

    private void defaultFilter() {
        sort = new Base("utime", "最新榜");
        type = new Tag(null, "全部");
        area = new Area(null, "全部");
        year = new Year(null, "全部");
    }

}
