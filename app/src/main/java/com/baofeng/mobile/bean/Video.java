package com.baofeng.mobile.bean;

import com.google.gson.Gson;

/**
 * 视频
 * 
 * @author liruiyu
 * 
 */
public class Video {
	/** 视频id */
	public String vid;
	/** 视频名称 */
	public String name;
	/** 封面 */
	public String cover;
	/** 推广语 intro*/
	public String desc;
    
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}


	public static class VideoInfo extends Video{
		/** 类别 */
		public int category;
		/** 简介 */
		public String desc;
		/** 播放量 */
		public int count;
		/** 视频源地址 */
		public String url;

		public VideoInfo(Video video){
			this.vid = video.vid;
			this.name = video.name;
			this.cover = video.cover;
			this.desc = video.desc;
		}
	}
}