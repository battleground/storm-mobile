package com.baofeng.mobile.bean;

/**
 * 分集的信息
 * 
 * @author liulipeng
 * 
 */
public class VideoSeries {

	public String tvid;
	public String id;// 分集的id
	public String name;// 名称
	public String vid;// 视频的id
	public String source;// 接入点
	public String num;// 第几集
	public String url;// 播放地址
	public int point;// 播放的历史时长
	public int duration;// 影片的播放总时长
	public long mins;// 视频的总长度
	public String cover;// 分集的封面
	public String sort;
	public String cannotplay;// 是否可以播放

	public boolean selected = false;

	public Video video;// 视频的信息

	public String desc;// 分集的简介

}
