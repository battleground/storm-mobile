package com.baofeng.mobile.fragment;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.abooc.common.Toast;
import com.baofeng.mobile.activity.VideoStorageActivity;
import com.baofeng.mobile.bean.Area;
import com.baofeng.mobile.bean.Base;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.FilterBean;
import com.baofeng.mobile.bean.Status;
import com.baofeng.mobile.bean.Tag;
import com.baofeng.mobile.bean.Year;
import com.baofeng.mobile.network2.JsonResponseHandler;
import com.baofeng.mobile.network2.VideoClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjunpu
 * @date 15/7/28
 */
public class VideoFilterPresenter {

    public static final String DEFAULT_CATEGORY = "2"; //默认分类

    private Category mCategory; //分类
    private Base sort; //排序
    private Tag type; //类型
    private Area area; //地区
    private Year year; //年限

    private List<FilterBean> mFilterList;

    private List<Base> mSortList;
    private List<Tag> mTypeList;
    private List<Area> mAreaList;
    private List<Year> mYearList;

    private VideoStorageActivity mViewer;

    public enum FilterType {
        SORT, TYPE, AREA, YEAR
    }

    public VideoFilterPresenter(VideoStorageActivity mViewer, Category category) {
        this.mViewer = mViewer;
        this.mCategory = category;
        if (mSortList == null) {
            mSortList = new ArrayList<>();
            mSortList.add(new Base("utime", "最新榜"));
            mSortList.add(new Base("plays", "人气榜"));
            mSortList.add(new Base("score", "好评榜"));
        }
        sort = mSortList.get(0);
    }

    public void setCategory(Category category){
        mCategory = category;
    }

    public void update(FilterType type, int position) {
        switch (type) {
            case SORT:
                this.sort = mSortList.get(position);
                break;
            case TYPE:
                this.type = mTypeList.get(position);
                break;
            case AREA:
                this.area = mAreaList.get(position);
                break;
            case YEAR:
                this.year = mYearList.get(position);
                break;
        }
    }

    public void load() {
        if (mFilterList == null || mFilterList.isEmpty()) {
            VideoClient client = new VideoClient();
            client.getVideoFilter(httpHandler);
        }
    }

    private JsonResponseHandler<Status<List<FilterBean>>> httpHandler = new JsonResponseHandler<Status<List<FilterBean>>>() {
        @Override
        public void onStart() {
            Toast.show("开始加载筛选数据");
        }

        @Override
        public void onFinish() {
            Toast.show("筛选数据加载完毕");
        }

        @Override
        public void onSuccess(int statusCode, Status<List<FilterBean>> response) {
            if (response.is200()) {
                List<FilterBean> list = response.getData();
                if (list == null || list.isEmpty()) {
                    return;
                }
                updateFilterData(list);
            } else {
                Snackbar.make(mViewer.getWindow().getDecorView(), response.getError_msg(), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, String error, String rawJsonData) {
            Snackbar.make(mViewer.getWindow().getDecorView(), error, Snackbar.LENGTH_SHORT).show();
        }
    };

    /**
     * 更新筛选数据
     * @param list
     */
    private void updateFilterData(List<FilterBean> list) {
        mFilterList = list;
        for (FilterBean filter : mFilterList) {
            if (!TextUtils.isEmpty(mCategory.id) && mCategory.id.equals(filter.getCategory())) {
                mTypeList = filter.getTags();
                mAreaList = filter.getAreas();
                mYearList = filter.getYears();
                mTypeList.add(0, new Tag(null, "全部"));
                mAreaList.add(0, new Area(null, "全部"));
                mYearList.add(0, new Year(null, "全部"));
                break;
            }
        }
        initTab();
    }

    /**
     * 初始化筛选数据
     */
    private void initTab() {
        if (mViewer != null) {
            mViewer.initTab(FilterType.SORT, mSortList);
            mViewer.initTab(FilterType.TYPE, mTypeList);
            mViewer.initTab(FilterType.AREA, mAreaList);
            mViewer.initTab(FilterType.YEAR, mYearList);
        }
    }

    public void close() {
        if (sort == null || type == null || area == null || year == null) {
            return;
        }
        String filter = appendFilterResult();
        if (!TextUtils.isEmpty(filter)) {
            saveFilter(filter);
        }
        doFilter();
    }

    /**
     * 保存筛选结果
     */
    private void saveFilter(String filter) {
        if (mViewer != null) {
            mViewer.saveFilter(filter);
        }
    }

    /**
     * 执行筛选结果
     */
    public void doFilter() {
        if (mViewer != null) {
            mViewer.doFilter(sort, type, area, year);
        }
    }

    /**
     * 生成、拼接筛选结果
     */
    private String appendFilterResult() {
        StringBuilder sb = new StringBuilder();
        sb.append(sort.getName()).append(" · ");
        if (type != null && !type.isDefault()) {
            sb.append(type.getName()).append(" · ");
        }
        if (area != null && !area.isDefault()) {
            sb.append(area.getName()).append(" · ");
        }
        if (year != null && !year.isDefault()) {
            sb.append(year.getName()).append(" · ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 3, sb.length());
        }
        return sb.toString();
    }

}
