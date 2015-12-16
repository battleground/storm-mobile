package com.baofeng.mobile.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;

import com.abooc.common.Log;
import com.abooc.common.Toast;
import com.baofeng.mobile.BuildConfig;
import com.baofeng.mobile.bean.Video;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zhangjunpu
 * @date 14/12/18
 */
public class ToolUtils {

    public static final boolean TOAST_LOADING_ALL = false;

    public static void debug(Object msg) {
        if (BuildConfig.DEBUG) {
            System.out.println(msg);
        }
    }

    public static int parseIntSafely(Object number) {
        String num = String.valueOf(number);
        return parseIntSafely(num);
    }

    public static int parseIntSafely(String number) {
        if (TextUtils.isEmpty(number) == "null".equalsIgnoreCase(number)){
            try {
                return Integer.parseInt(number);
            } catch (NumberFormatException exception) {
                Log.e("NumberFormatException:" + number + ", " + exception.getMessage());
                exception.printStackTrace();
                return 0;
            }
        }
        Log.e("NumberFormatException number = \"" + number + "\"");
        return 0;
    }

    public static boolean parseBooleanSafely(Object flag) {
        String num = String.valueOf(flag);
        return parseBooleanSafely(num);
    }

    public static boolean parseBooleanSafely(String flag) {
        if (TextUtils.isEmpty(flag) || "null".equalsIgnoreCase(flag)) return false;
        try {
            return Boolean.parseBoolean(flag);
        } catch (NumberFormatException exception) {
            Log.e("NumberFormatException:" + flag + ", " + exception.getMessage());
            exception.printStackTrace();
        }
        return false;
    }

    public static float parseFloatSafely(String number) {
        if (TextUtils.isEmpty(number) || "null".equalsIgnoreCase(number)) return 0;
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException exception) {
            Log.e("NumberFormatException:" + number + ", " + exception.getMessage());
            exception.printStackTrace();
        }
        return 0;
    }

    public static Long parseLongSafely(String number) {
        if (TextUtils.isEmpty(number) || "null".equalsIgnoreCase(number)) return 0L;
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException exception) {
            Log.e("NumberFormatException:" + number + ", " + exception.getMessage());
            exception.printStackTrace();
        }
        return 0L;
    }

    /**
     * 短时间提示
     * @param msg
     */
    public static void toast(String msg) {
        if (TOAST_LOADING_ALL) {
            Toast.show(msg);
        }
    }

    /**
     * 已加载全部提示
     */
    public static void toastAll() {
        if (TOAST_LOADING_ALL) {
            Toast.show("已加载全部");
        }
    }

    /**
     * 获取应用版本号
     */
    public static String getVersionCode(Context context) {
        try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			int code = packInfo.versionCode;
			return String.valueOf(code);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        return "";
    }

    /**
     * 返回视频集合中所有视频的ids串
     * @param list
     * @return
     */
    public static String getVideoIds(List<? extends Video> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Video video : list) {
            sb.append(video.vid).append(",");
            index++;
        }
        if (index > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    /**
     * 用户删除播放历史
     * 返回视频集合中所有视频的ids串
     * @param list
     * @return
     */
    public static String getVideoHistoryIds(List<? extends Video> list) {
//        if (list == null || list.isEmpty()) {
            return null;
//        }
//        StringBuilder sb = new StringBuilder();
//        int index = 0;
//        for (Video video : list) {
//            sb.append(video.history_id).append(",");
//            index++;
//        }
//        if (index > 0) {
//            sb.delete(sb.length() - 1, sb.length());
//        }
//        return sb.toString();
    }


    /**
     * 秒转时间
     * @param ctimeStr 秒
     * @return
     */
    public static String getTimeFromSecond(String ctimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long longtime = Long.valueOf(ctimeStr);
        String sd = sdf.format(new Date(longtime * 1000L));
        return sd;
    }

    /**
     * 毫秒转时间
     * @param ctimeStr 毫秒
     * @return
     */
    public static String getTimeFromMillisecond(String ctimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long longtime = Long.valueOf(ctimeStr);
        String sd = sdf.format(new Date(longtime));
        return sd;
    }
//
//    if (month == 2) {
//        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
//            day = 29;
//        } else {
//            day = 28;
//        }
//    } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
//        day = 30;
//    }
//
    public static String getDiffTime(long ctime) {
        long curTime = System.currentTimeMillis() / 1000;
        long times = curTime - ctime;

        long oneYear = 365 * 24 * 60 * 60; // 一年的秒数
        long oneMonth = 30 * 24 * 60 * 60; // 一个月的秒数
        long oneDay = 24 * 60 * 60; // 一天的秒数
        long oneHour = 60 * 60; // 一小时的秒数
        long oneMinute = 60; //一分钟的秒数

        String result = "刚刚";
        if (times > oneYear) {
            result = times / oneYear + "年前";
        } else if (times > oneMonth) {
            result = times / oneMonth + "个月前";
        } else if (times > oneDay) {
            result = times / oneDay + "天前";
        } else if (times > oneHour) {
            result = times / oneHour + "小时前";
        } else if (times > oneMinute) {
            result = times / oneMinute + "分钟前";
        } else if (times > 10) {
            result = times + "秒前";
        } else if (times >= 0) {
            result = "刚刚";
        }
        return result;
    }
    /**
     * 获取时间差
     * @param ctime
     * @return
     */
    public static String getDiffTime(String ctime) {
        long time = 0;
        try {
            time = Long.parseLong(ctime);
        } catch (NumberFormatException exception) {
            Log.e("NumberFormatException:" + ctime);
        }
        return getDiffTime(time);
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
         /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
             /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
             /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                 /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                 /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 获取长度为
     * @param value
     * @param maxLength
     * @return
     */
    public static String chineseSub(String value, int maxLength) {
        int valueLength = 0;
        String subStr = null;
        String chinese = "[\u0391-\uFFE5]";
         /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
             /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
             /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                 /* 中文字符长度为2 */
                if (valueLength == maxLength - 1) {
                    subStr = value.substring(0, i);
                }
                valueLength += 2;
            } else {
                 /* 其他字符长度为1 */
                valueLength += 1;
            }
            if (valueLength == maxLength) {
                subStr = value.substring(0, i + 1);
            }
        }
        if (valueLength < maxLength) {
            return value;
        } else {
            return subStr;
        }
    }

    public static void backForResult(Activity activity, Bundle args) {
        backForResult(activity, Activity.RESULT_OK, args);
    }

    public static void backForResult(Activity activity, int resultCode, Bundle args) {
        Intent intent = new Intent();
        if (args != null) {
            intent.putExtras(args);
        }
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    /**
     * 获得下一页页码
     * @param count 当前列表总数
     * @param pageSize 每页最大数量
     * @return 下一页页数
     */
    public static int getNextPager(int count, int pageSize) {
        int nextPage;
        if (count % pageSize != 0) {
            nextPage = count / pageSize + 1 + 1;
        } else {
            nextPage = count / pageSize + 1;
        }
        return nextPage;
    }

    /**
     * 高亮显示已选数字
     * @param formate 文字格式
     * @param count 数量
     * @param start 开始位置
     * @return
     */
    public static SpannableString getSSText(String formate, int count, int start, int color) {
        // 高亮显示个数
		SpannableString ss = new SpannableString("");
		try {
			String result = String.format(formate, count);
			ss = new SpannableString(result);
			int end = start + String.valueOf(count).length();
			ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		} catch (Throwable e) {
			e.printStackTrace();
		}
        return ss;
    }

    /**
     * 高亮显示已选数字
     * @param formate 文字格式
     * @param count 数量
     * @param start 开始位置
     * @return
     */
    public static SpannableString getSSText(String formate, float count, int start, int color) {
        String result = String.format(formate, count);
        // 高亮显示个数
        SpannableString ss = new SpannableString(result);
        int end = start + String.valueOf(count).length();
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 高亮显示已选数字
     * @param formate 文字格式
     * @param text 要变色的文字
     * @param start 开始位置
     * @param color 颜色
     * @return
     */
    public static SpannableString getSSText(String formate, String text, int start, int color) {
        String result = String.format(formate, text);
        // 高亮显示个数
        SpannableString ss = new SpannableString(result);
        int end = start + String.valueOf(text).length();
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 返回dp值
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 返回dp值
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取网络的ip地址
     * @return
     */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}
    
    /**
     * 获取本机的mac地址
     * @param context
     * @return
     */
	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

    /**
     * 数字过万后以W表示
     * @param count
     * @return
     */
    public static String getFuzzyCount(String count) {
        int countAll = parseIntSafely(count);
        return getFuzzyCount(countAll);
    }

    /**
     * 数字过万后以W表示
     * @param count
     * @return
     */
    public static String getFuzzyCount(int count) {
        if (count > 9999) {
            return getSectionStr(count / 10000) + "W";
        }
        return getSectionStr(count);
    }

    /**
     * 字符串每三位增加一个","
     * @param count
     * @return
     */
    public static String getSectionStr(String count) {
        int countAll = parseIntSafely(count);
        return getSectionStr(countAll);
    }

    /**
     * 字符串每三位增加一个","
     * @param count
     * @return
     */
    public static String getSectionStr(int count) {
        return String.format("%,d", count);
    }

    /**
     * 获取小数的字符串形态
     * @param count
     * @return
     */
    public static String getStrFloat(float count) {
        if (count * 10 % 10 != 0) {
            return String.valueOf(count);
        } else {
            return String.valueOf((int) count);
        }
    }

    /**
     * 所有字符全角化，解决TextView内文字参差不齐
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getSpace() {
        return "\u3000\u3000";
    }

    /**
     * 判断time是否在当前时间24小时内
     */
    public static boolean isNew24Hours(String time) {
        long rectime = parseIntSafely(time);
        return isNew24Hours(rectime);
    }

    /**
     * 判断time是否在当前时间24小时内
     */
    public static boolean isNew24Hours(long time) {
        long rectime = time * 1000;
        long ctime = System.currentTimeMillis();
        return ctime - rectime < 24 * 60 * 60 * 1000;
    }

    /**
     * 金币字体
     */
    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/ExtremeLeet.ttf");
    }

    /**
     * 根据当前时间和出生日期判断年龄
     * @param birthDay 出生日期Date
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };

    /**
     * 根据出生日期判断星座
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    /**
     * 全角化文字、TextView排版
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断当前网络是否可用
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        try {
            // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(e.toString());
        }
        return false;
    }

    /**
     * 获取actionBar高度
     */
    public static int getActionBarSize(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }
}
