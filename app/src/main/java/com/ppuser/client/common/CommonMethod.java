package com.ppuser.client.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ppuser.client.R;
import com.ppuser.client.utils.LogUtil;
import com.ppuser.client.utils.ScreenUtil;
import com.ppuser.client.view.photo.BigImageActivity;
import com.ppuser.client.view.weight.BadgeView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.lines;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 根据什么得到什么的工具类方法
 *
 * @author huangfucai
 *
 */
public class CommonMethod {
	/**
	 * 根据年月计算本月天数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
			case 0:
				flag = true;
				break;
			default:
				flag = false;
				break;
		}
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = 31;
				break;
			case 2:
				day = flag ? 29 : 28;
				break;
			default:
				day = 30;
				break;
		}
		return day;
	}

	/**
	 * 根据生日计算年龄 生日格式：xx-xx-xx
	 *
	 * @param birthday
	 * @return
	 */
	@SuppressLint({ "SimpleDateFormat", "UseValueOf" })
	public static final String calculateDatePoor(String birthday) {
		try {
			if (TextUtils.isEmpty(birthday))
				return "0";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdayDate = sdf.parse(birthday);
			String currTimeStr = sdf.format(new Date());
			Date currDate = sdf.parse(currTimeStr);
			if (birthdayDate.getTime() > currDate.getTime()) {
				return "0";
			}
			long age = (currDate.getTime() - birthdayDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
			String year = new DecimalFormat("0.00").format(age / 365f);
			if (TextUtils.isEmpty(year))
				return "0";
			return String.valueOf(new Double(year).intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}

	/**
	 * 根据月日计算星座
	 *
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getAstro(int month, int day) {
		String[] astro = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座",
				"射手座", "摩羯座" };
		int[] arr = new int[] { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int index = month;
		if (day < arr[month - 1]) {
			index = index - 1;
		}
		return astro[index];
	}

	/**
	 * 两经纬度间的距离 把地球当成圆球来处理
	 *
	 * @param y1
	 * @param x1
	 * @param y2
	 * @param x2
	 * @return
	 */
	public static double getDistance(double y1, double x1, double y2, double x2) {
		double PI = 3.14159265358979323;
		double R = 6371229;
		double x, y, distance;
		x = (x2 - x1) * PI * R * Math.cos(((y1 + y2) / 2) * PI / 180) / 180;
		y = (y2 - y1) * PI * R / 180;
		distance = Math.hypot(x, y);
		return distance;
	}

	/**
	 * 两经纬度间的距离 Android的提供的接口
	 *
	 * @param y1
	 * @param x1
	 * @param y2
	 * @param x2
	 * @return
	 */
	public static double getDistanceForAndroid(double y1, double x1, double y2, double x2) {
		int MAXITERS = 20;
		y1 *= Math.PI / 180.0;
		y2 *= Math.PI / 180.0;
		x1 *= Math.PI / 180.0;
		x2 *= Math.PI / 180.0;
		double a = 6378137.0;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);
		double L = x2 - x1;
		double A = 0.0;
		double U1 = Math.atan((1.0 - f) * Math.tan(y1));
		double U2 = Math.atan((1.0 - f) * Math.tan(y2));
		double cosU1 = Math.cos(U1);
		double cosU2 = Math.cos(U2);
		double sinU1 = Math.sin(U1);
		double sinU2 = Math.sin(U2);
		double cosU1cosU2 = cosU1 * cosU2;
		double sinU1sinU2 = sinU1 * sinU2;
		double sigma = 0.0;
		double deltaSigma = 0.0;
		double cosSqAlpha = 0.0;
		double cos2SM = 0.0;
		double cosSigma = 0.0;
		double sinSigma = 0.0;
		double cosLambda = 0.0;
		double sinLambda = 0.0;
		double lambda = L;
		for (int iter = 0; iter < MAXITERS; iter++) {
			double lambdaOrig = lambda;
			cosLambda = Math.cos(lambda);
			sinLambda = Math.sin(lambda);
			double t1 = cosU2 * sinLambda;
			double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
			double sinSqSigma = t1 * t1 + t2 * t2;
			sinSigma = Math.sqrt(sinSqSigma);
			cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double sinAlpha = (sinSigma == 0) ? 0.0 : cosU1cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
			cos2SM = (cosSqAlpha == 0) ? 0.0 : cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha;
			double uSquared = cosSqAlpha * aSqMinusBSqOverBSq;
			A = 1 + (uSquared / 16384.0) * (4096.0 + uSquared * (-768 + uSquared * (320.0 - 175.0 * uSquared)));
			double B = (uSquared / 1024.0) * (256.0 + uSquared * (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
			double C = (f / 16.0) * cosSqAlpha * (4.0 + f * (4.0 - 3.0 * cosSqAlpha));
			double cos2SMSq = cos2SM * cos2SM;
			deltaSigma = B * sinSigma * (cos2SM + (B / 4.0) * (cosSigma * (-1.0 + 2.0 * cos2SMSq)
					- (B / 6.0) * cos2SM * (-3.0 + 4.0 * sinSigma * sinSigma) * (-3.0 + 4.0 * cos2SMSq)));
			lambda = L + (1.0 - C) * f * sinAlpha
					* (sigma + C * sinSigma * (cos2SM + C * cosSigma * (-1.0 + 2.0 * cos2SM * cos2SM)));
			double delta = (lambda - lambdaOrig) / lambda;
			if (Math.abs(delta) < 1.0e-12) {
				break;
			}
		}
		return (b * A * (sigma - deltaSigma))/1000;
	}

	/**
	 * #.## 表示会去掉小数点后最后的0 例如 1.20 就会变成1.2
	 *
	 * 0.00 表示不会去掉小数点后的0 例如 1.20 依然是 1.20
	 *
	 * @param nowDouble
	 * @param textformat
	 * @return
	 */
	public static String getSavePoint(double nowDouble, String textformat) {
		return new DecimalFormat(textformat).format(nowDouble);
	}

	/**
	 * 整百转换
	 *
	 * @param nowDouble
	 *            double 转整百时nowInt为零
	 * @param nowInt
	 *            int 转整百时nowDouble为零
	 * @return
	 */
	public static double getSaveHundred(double nowDouble, int nowInt) {
		if (nowDouble != 0) {
			return (double) (nowDouble - (nowDouble % 100)) / 1000;
		} else {
			return (double) (nowInt - (nowInt % 100)) / 1000;
		}

	}

	/**
	 * 设置activity透明度
	 *
	 * @param f
	 *            透明度，0代表全透明，0.5代表半透明，1代表完不透明
	 * @param window
	 *            当前activity的getwindow
	 */
	public static void setWindowAlpha(float f, Window window) {
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = f;
		window.setAttributes(lp);
	}

	/**
	 * 特殊字符串转换
	 *
	 * @param keyWord
	 *            需要转换的字符
	 * @return
	 */
	public static String sqliteEscape(String keyWord) {
		keyWord = keyWord.replace("/", "//");
		keyWord = keyWord.replace("'", "''");
		keyWord = keyWord.replace("[", "/[");
		keyWord = keyWord.replace("]", "/]");
		keyWord = keyWord.replace("%", "/%");
		keyWord = keyWord.replace("&", "/&");
		keyWord = keyWord.replace("_", "/_");
		keyWord = keyWord.replace("(", "/(");
		keyWord = keyWord.replace(")", "/)");
		return keyWord;
	}

	/**
	 * 根据layou获取view
	 *
	 * @param context
	 *            上下文
	 * @param layout
	 *            R.layout.id
	 * @return
	 */
	public static View getView(Context context, int layout) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(layout, null);
	}

	/**
	 * 根据layou获取view
	 *
	 * @param context
	 *            上下文
	 * @param layout
	 *            R.layout.id
	 * @return
	 */
	public static View getView(Context context, int layout, ViewGroup root) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(layout, root);
	}

	/**
	 * 根据layou获取view
	 *
	 * @param context
	 *            上下文
	 * @param layout
	 *            R.layout.id
	 * @return
	 */
	public static View getView(Context context, int layout, ViewGroup root, boolean flag) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(layout, root, flag);
	}

	/**
	 * 显示软键盘
	 *
	 * @param view
	 *            触发显示软件盘的view
	 * @param inputMethodManager=(InputMethodManager)
	 *            getSystemService(Context.INPUT_METHOD_SERVICE);
	 */
	public static void showSoftInput(View view, InputMethodManager inputMethodManager) {
		inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	/**
	 * 隐藏软键盘
	 *
	 * @param window
	 *            window.getwindow
	 * @param view
	 *            触发控件
	 * @param inputMethodManager
	 *            = (InputMethodManager)
	 *            getSystemService(Context.INPUT_METHOD_SERVICE);
	 */
	public static void hideKeyboard(Window window, View view, InputMethodManager inputMethodManager) {
		if (window.getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 根据图片路径、屏幕宽高获取bitmap
	 *
	 * @param path
	 *            图片路径
	 * @param rqsW
	 *            屏幕宽
	 * @param rqsH
	 *            屏幕高
	 * @return
	 */
	public static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int ow = options.outWidth;
		int oh = options.outHeight;
		int size = (ow * oh) / (rqsW * rqsH);
		if (size % 2 != 0) {
			size++;
		}
		options.inSampleSize = size;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 根据图片路径、屏幕宽高获取bitmap
	 *
	 * @param path
	 *            图片路径
	 * @param rqsW
	 *            屏幕宽
	 * @param rqsH
	 *            屏幕高
	 * @return
	 */
	public static Bitmap compressBitmapForNewGoods(String path, int rqsW, int rqsH, int compressSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int ow = options.outWidth;
		int oh = options.outHeight;
		options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH, ow, oh) * compressSize;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 计算压缩值
	 *
	 * @param options
	 *            BitmapFactory.Options
	 * @param rqsW
	 *            屏幕宽
	 * @param rqsH
	 *            屏幕高
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @return
	 */
	public static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH, int width, int height) {
		int inSampleSize = 1;
		if (rqsW == 0 || rqsH == 0)
			return 1;
		if (height > rqsH || width > rqsW) {
			final int heightRatio = Math.round((float) height / (float) rqsH);
			final int widthRatio = Math.round((float) width / (float) rqsW);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 图片的质量压缩（尺寸不变－即图片像素点不变，空间表小）
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressBmpFromBmp(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		return BitmapFactory.decodeStream(isBm, null, null);
	}

	/**
	 * 监听某控件是否已经画好
	 *
	 * @param v
	 */
	public static void setViewCancelListener(View v, OnGlobalLayoutListener onGlobalLayoutListener) {
		ViewTreeObserver vto = v.getViewTreeObserver();// 监听某控件是否已经绘画
		vto.addOnGlobalLayoutListener(onGlobalLayoutListener);
	}
	/**
	 * 直接测量计算一个view的宽高width,height
	 *
	 * @param view
	 * @return width height
	 */
	public static Map<String, Integer> getViewHW(View view) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		int intw=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		view.measure(intw, inth);
		int viewWidth = view.getMeasuredWidth();
		int viewHeight = view.getMeasuredHeight();
		map.put("width", viewWidth);
		map.put("height", viewHeight);
		return map;
	}

	/**
	 * 发送短信
	 *
	 * @param phoneNumber
	 * @param sendContent
	 */
	public static void sendSMSMessage(String phoneNumber, String sendContent) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNumber, null, sendContent, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拨打电话
	 *
	 */
	public static void makeCall(Context context, String phoneNumber) {

		Intent phoneIntent = new Intent(Intent.ACTION_CALL);
		phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		phoneIntent.setData(Uri.parse("tel:" + phoneNumber + ""));
		try {
			context.startActivity(phoneIntent);
		} catch (android.content.ActivityNotFoundException ex) {

		}
	}

	/**
	 * 判断一个字符串是否是英文字母
	 *
	 * @param s
	 * @return
	 */
	public static boolean isEnglish(String s) {
		char c = s.charAt(0);
		int i = (int) c;
		if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某字符串是否是float型,返回true表示是，false表示不是
	 *
	 * @param testString
	 * @return
	 */
	public static boolean isNumberString(String testString) {
		String numAllString = "0123456789";
		if (testString.length() <= 0) {
			return false;
		}
		for (int i = 0; i < testString.length(); i++) {
			String charInString = testString.substring(i, i + 1);
			if (!numAllString.contains(charInString)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * 返回首字母
	 *
	 * @param strChinese
	 *
	 * @param bUpCase
	 *
	 * @return
	 *
	 */

	public static String getPYIndexStr(String strChinese, boolean bUpCase) {

		try {

			StringBuffer buffer = new StringBuffer();

			byte b[] = strChinese.getBytes("GBK");// 把中文转化成byte数组

			for (int i = 0; i < b.length; i++) {

				if ((b[i] & 255) > 128) {

					int char1 = b[i++] & 255;

					char1 <<= 8;// 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方

					int chart = char1 + (b[i] & 255);

					buffer.append(getPYIndexChar((char) chart, bUpCase));

					continue;

				}

				char c = (char) b[i];

				if (!Character.isJavaIdentifierPart(c))

					c = 'A';

				buffer.append(c);

			}

			return buffer.toString();

		} catch (Exception e) {

			System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519")
					.append(e.getMessage()).toString());

		}

		return null;

	}

	/**
	 *
	 * 得到首字母
	 *
	 * @param strChinese
	 *
	 * @param bUpCase
	 *
	 * @return
	 *
	 */
	public static char getPYIndexChar(char strChinese, boolean bUpCase) {
		int charGBK = strChinese;
		char result;
		if (charGBK >= 45217 && charGBK <= 45252)
			result = 'A';
		else
		if (charGBK >= 45253 && charGBK <= 45760)
			result = 'B';
		else
		if (charGBK >= 45761 && charGBK <= 46317)
			result = 'C';
		else
		if (charGBK >= 46318 && charGBK <= 46825)
			result = 'D';
		else
		if (charGBK >= 46826 && charGBK <= 47009)
			result = 'E';
		else
		if (charGBK >= 47010 && charGBK <= 47296)
			result = 'F';
		else
		if (charGBK >= 47297 && charGBK <= 47613)
			result = 'G';
		else
		if (charGBK >= 47614 && charGBK <= 48118)
			result = 'H';
		else
		if (charGBK >= 48119 && charGBK <= 49061)
			result = 'J';
		else
		if (charGBK >= 49062 && charGBK <= 49323)
			result = 'K';
		else
		if (charGBK >= 49324 && charGBK <= 49895)
			result = 'L';
		else
		if (charGBK >= 49896 && charGBK <= 50370)
			result = 'M';
		else
		if (charGBK >= 50371 && charGBK <= 50613)
			result = 'N';
		else
		if (charGBK >= 50614 && charGBK <= 50621)
			result = 'O';
		else
		if (charGBK >= 50622 && charGBK <= 50905)
			result = 'P';
		else
		if (charGBK >= 50906 && charGBK <= 51386)
			result = 'Q';
		else
		if (charGBK >= 51387 && charGBK <= 51445)
			result = 'R';
		else
		if (charGBK >= 51446 && charGBK <= 52217)
			result = 'S';
		else
		if (charGBK >= 52218 && charGBK <= 52697)
			result = 'T';
		else
		if (charGBK >= 52698 && charGBK <= 52979)
			result = 'W';
		else
		if (charGBK >= 52980 && charGBK <= 53688)
			result = 'X';
		else
		if (charGBK >= 53689 && charGBK <= 54480)
			result = 'Y';
		else
		if (charGBK >= 54481 && charGBK <= 55289)
			result = 'Z';
		else
			result = (char) (65 + (new Random()).nextInt(25));
		if (!bUpCase)
			result = Character.toLowerCase(result);
		return result;

	}

	/**
	 * 获取一种Color
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getColor(Context context, int id) {
//		int version = Build.VERSION.SDK_INT;
//		if (version >= 23) {
//			return ContextCompat.getColor(context, id);
//		} else {
		return context.getResources().getColor(id);
//		}
	}

	/**
	 * 获得一个Drawable
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Drawable getDrawable(Context context, int resourcesId) {
		int version = Build.VERSION.SDK_INT;
		if (version >= 23) {
			return ContextCompat.getDrawable(context, resourcesId);
		} else {
			return context.getResources().getDrawable(resourcesId);
		}
	}

	/**
	 * 判断指定的字符串是否是 正确的（不为“”、null 、“null”）
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 实现文本复制功能
	 *
	 * @param content
	 */
	@SuppressWarnings("deprecation")
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 实现粘贴功能
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	/**
	 * 获取本地视频缩略图
	 *
	 * @param videoPath
	 * @return
	 */
	public static Bitmap getVideoThumbnail(String videoPath) {
		MediaMetadataRetriever media = new MediaMetadataRetriever();
		media.setDataSource(videoPath);
		Bitmap bitmap = media.getFrameAtTime();
		return bitmap;
	}

	/**
	 * 图片压缩
	 *
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int width, int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 图片压缩(项目中的图片)未完成
	 *
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getSmallBitmapForDecodeResource(Resources resources, int filePath, int width, int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources,filePath);
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(resources,filePath);
	}

	/**
	 * 计算图片的缩放值
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 4;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 *  传入一个Bitmap获得指定大小的图片   
	 *
	 * @param bm 所要转换的bitmap  
	 * @param newWidth 新的宽
	 * @param newHeight 新的高
	 * @return 指定宽高的bitmap
	 */
	public static Bitmap getBitmap(Bitmap bm, int newWidth ,int newHeight){
		int width = bm.getWidth();
		int height = bm.getHeight();
		/**
		 * 计算缩放比例
		 */
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		/**
		 *  取得想要缩放的matrix参数
		 */
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		/**
		 *  得到新的图片
		 */
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}
	/**
	 * 通过视频地址获取该视频的分辨率
	 *
	 * @param videoPath
	 * @return
	 */
	public static Map<String, String> getVideoResolution(String videoPath){
		Map<String, String> map=new HashMap<String, String>();
		MediaMetadataRetriever retr = new MediaMetadataRetriever();
		retr.setDataSource(videoPath);
		String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
		String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
		map.put("videoWidth", width);
		map.put("videoHeight", height);
		return map;

	}

	/**
	 * 状态栏的隐藏与显示
	 *
	 * @param window
	 * @param enable
	 */
	public static void showStatus(Window window,boolean enable) {
		if (!enable) {//隐藏状态栏
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			window.setAttributes(lp);
		} else {//显示状态栏
			WindowManager.LayoutParams attr = window.getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			window.setAttributes(attr);
		}
	}

	/**
	 * 判断是否为手机号码
	 *
	 * @param number
	 * @return
	 */
	public static boolean isPhoneNumber(String number){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	public static byte[] getBitmapByte(Bitmap bitmap){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static double getTwoNumber(double number,int count){
		BigDecimal b = new BigDecimal(number);
		return b.setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void setTextViewDrawable(Context context,TextView textView,int resources,int compound){
		Drawable drawable= context.getResources().getDrawable(resources);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		if (compound==1){
			textView.setCompoundDrawables(drawable,null,null,null);
		}else if (compound==2){
			textView.setCompoundDrawables(null,drawable,null,null);
		}else if (compound==3){
			textView.setCompoundDrawables(null,null,drawable,null);
		}else if (compound==4){
			textView.setCompoundDrawables(null,null,null,drawable);
		}

	}

	/**
	 * 带有数字的小红点
	 *
	 * @param view
	 * @param count
	 */
	public static void initBadgeView(BadgeView badgeView, View view, int count) {
		badgeView.setBackgroundResource(R.drawable.ease_unread_count_bg);
		badgeView.setIncludeFontPadding(false);
		badgeView.setGravity(Gravity.CENTER);
		badgeView.setTextSize(12);
		badgeView.setTextColor(Color.WHITE);
		// 设置提示信息
		badgeView.setBadgeCount(count);
		// 添加依附的对象View
		badgeView.setTargetView(view);

	}

	public static File getFileByUri(Uri uri, Activity activity) {
		String path = null;
		if ("file".equals(uri.getScheme())) {
			path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = activity.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
				Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
				int index = 0;
				int dataIdx = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
					index = cur.getInt(index);
					dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					path = cur.getString(dataIdx);
				}
				cur.close();
				if (index == 0) {
				} else {
					Uri u = Uri.parse("content://media/external/images/media/" + index);
					System.out.println("temp uri is :" + u);
				}
			}
			if (path != null) {
				return new File(path);
			}
		} else if ("content".equals(uri.getScheme())) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
			cursor.close();

			return new File(path);
		} else {
			LogUtil.t("Uri Scheme:" + uri.getScheme());
		}
		return null;
	}

	/**
	 * 大图浏览
	 *
	 * @param context
	 * @param position
	 * @param urls
	 */
	public static void bigImageBrower(Context context, int position, String[] urls) {
		Intent intent = new Intent(context, BigImageActivity.class);
		intent.putExtra("image_urls", urls);
		intent.putExtra("image_index", position);
		context.startActivity(intent);
	}

	public static void addFragment(FragmentManager manager, Fragment fragment, int container) {
		removeFragment(manager,fragment);

		String tag = fragment.toString();
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(container, fragment, tag);
		ft.commitAllowingStateLoss();
		manager.executePendingTransactions();
	}

	public static void removeFragment(FragmentManager manager, Fragment fragment) {
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(fragment);
		ft.commitAllowingStateLoss();
		manager.executePendingTransactions();
	}

	public static boolean toStartOtherApplication(Activity activity,String packagename) {
		PackageInfo packageinfo = null;
		try {
			packageinfo = activity.getPackageManager().getPackageInfo(packagename, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageinfo == null) {
			return false;
		}

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageinfo.packageName);

		List<ResolveInfo> resolveinfoList = activity.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo resolveinfo = resolveinfoList.iterator().next();
		if (resolveinfo != null) {
			String packageName = resolveinfo.activityInfo.packageName;
			String className = resolveinfo.activityInfo.name;
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			activity.startActivity(intent);
		}
		return true;
	}

	public static void jumpActivity(Context context,Class<?> tClass){
		Intent intent = new Intent();
		intent.setClass(context,tClass);
		context.startActivity(intent);
	}

	public static void jumpActivity(Context context,Class<?> tClass,int type){
		Intent intent = new Intent();
		intent.setClass(context,tClass);
		intent.putExtra("type",type);
		context.startActivity(intent);
	}

	public static String getDeviceId(Context context){
		TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
		return TelephonyMgr.getDeviceId();
	}

	/**
	 * 设置listview高度的方法
	 * @param listView
	 */
	public static void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static void initlinearLayoutForEditAbility(LinearLayout parentLL, String[] tags) {
		parentLL.removeAllViews();
		Context context=parentLL.getContext();
		/**
		 * 添加 TextView 的个数
		 */
		int textViewCount = tags.length;
		/**
		 * 每行的 LinearLayout
		 */
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(10, 10, 0, 10);
		ArrayList<TextView> childBtns = new ArrayList<>();
		int totoalTextViews = 0;
		for (int i = 0; i < textViewCount; i++) {
			final int position=i;
			totoalTextViews++;
			String item = tags[i];
			if (tags.length==1&&item.length()==0){
				return;
			}
			int textWidth;
			textWidth=ScreenUtil.getTextViewLength(new TextView(context),item)+ ScreenUtil.dip2px(context,20);
			/**
			 * 每个 item 的属性
			 */
			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(textWidth,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			itemParams.setMargins(15, 15, 15, 15);

			final TextView childTv =new TextView(context);
			childTv.setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
			childTv.setTextColor(parentLL.getResources().getColor(R.color.app_green));

			childTv.setGravity(Gravity.CENTER);
			childTv.setLayoutParams(itemParams);
			childTv.setPadding(10,5,10,5);
			childTv.setText(item);
			childTv.setTag(item);
			childTv.setTextSize(12);

			childBtns.add(childTv);

			if (totoalTextViews >= lines) {
				LinearLayout horizLL = new LinearLayout(context);
				horizLL.setOrientation(LinearLayout.HORIZONTAL);
				horizLL.setLayoutParams(layoutParams);

				for (TextView addBtn : childBtns) {
					horizLL.addView(addBtn);
				}
				parentLL.addView(horizLL);
				childBtns.clear();
				totoalTextViews = 0;
			}
		}
		/**
		 * 添加最后一行
		 */
		if (!childBtns.isEmpty()) {
			LinearLayout horizLL = new LinearLayout(context);
			horizLL.setOrientation(LinearLayout.HORIZONTAL);
			horizLL.setLayoutParams(layoutParams);

			for (TextView addBtn : childBtns) {
				horizLL.addView(addBtn);
			}
			parentLL.addView(horizLL);
			childBtns.clear();
			totoalTextViews = 0;
		}
	}

	public static void initlinearLayoutForSelectPeiyou(LinearLayout parentLL, String[] tags) {
		parentLL.removeAllViews();
		Context context=parentLL.getContext();
		/**
		 * 添加 TextView 的个数
		 */
		int textViewCount = tags.length;
		/**
		 * 每行的 LinearLayout
		 */
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 10, 0, 10);
		ArrayList<TextView> childBtns = new ArrayList<>();
		int totoalTextViews = 0;
		for (int i = 0; i < textViewCount; i++) {
			final int position=i;
			totoalTextViews++;
			String item = tags[i];
			if (tags.length==1&&item.length()==0){
				return;
			}
			int textWidth;
			textWidth=ScreenUtil.getTextViewLength(new TextView(context),item)+ScreenUtil.dip2px(context,20);
			/**
			 * 每个 item 的属性
			 */
			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(textWidth,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			itemParams.setMargins(0, 15, 15, 15);

			final TextView childTv =new TextView(context);
			childTv.setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
			childTv.setTextColor(parentLL.getResources().getColor(R.color.white));

			childTv.setGravity(Gravity.CENTER);
			childTv.setLayoutParams(itemParams);
			childTv.setPadding(10,5,10,5);
			childTv.setText(item);
			childTv.setTag(item);
			childTv.setTextSize(14);

			childBtns.add(childTv);

			if (totoalTextViews >= lines) {
				LinearLayout horizLL = new LinearLayout(context);
				horizLL.setOrientation(LinearLayout.HORIZONTAL);
				horizLL.setLayoutParams(layoutParams);

				for (TextView addBtn : childBtns) {
					horizLL.addView(addBtn);
				}
				parentLL.addView(horizLL);
				childBtns.clear();
				totoalTextViews = 0;
			}
		}
		/**
		 * 添加最后一行
		 */
		if (!childBtns.isEmpty()) {
			LinearLayout horizLL = new LinearLayout(context);
			horizLL.setOrientation(LinearLayout.HORIZONTAL);
			horizLL.setLayoutParams(layoutParams);

			for (TextView addBtn : childBtns) {
				horizLL.addView(addBtn);
			}
			parentLL.addView(horizLL);
			childBtns.clear();
			totoalTextViews = 0;
		}
	}

	public static void initlinearLayoutForSelectPeiyou(LinearLayout parentLL, String[] tags,Boolean choose) {
		parentLL.removeAllViews();
		Context context=parentLL.getContext();
		/**
		 * 添加 TextView 的个数
		 */
		int textViewCount = tags.length;
		/**
		 * 每行的 LinearLayout
		 */
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 10, 0, 10);
		ArrayList<TextView> childBtns = new ArrayList<>();
		int totoalTextViews = 0;
		if(textViewCount>4) {
			textViewCount = 4;
		}
		for (int i = 0; i < textViewCount; i++) {
			final int position=i;
			totoalTextViews++;
			String item = tags[i];
			if (tags.length==1&&item.length()==0){
				return;
			}
			int textWidth;
			textWidth=ScreenUtil.getTextViewLength(new TextView(context),item)+ScreenUtil.dip2px(context,20);
			/**
			 * 每个 item 的属性
			 */
			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(textWidth,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			itemParams.setMargins(0, 15, 15, 15);

			final TextView childTv =new TextView(context);
			childTv.setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
			childTv.setTextColor(parentLL.getResources().getColor(R.color.white));

			childTv.setGravity(Gravity.CENTER);
			childTv.setLayoutParams(itemParams);
			childTv.setPadding(10,5,10,5);
			childTv.setText(item);
			childTv.setTag(item);
			childTv.setTextSize(14);

			childBtns.add(childTv);

			if (totoalTextViews >= lines) {
				LinearLayout horizLL = new LinearLayout(context);
				horizLL.setOrientation(LinearLayout.HORIZONTAL);
				horizLL.setLayoutParams(layoutParams);

				for (TextView addBtn : childBtns) {
					horizLL.addView(addBtn);
				}
				parentLL.addView(horizLL);
				childBtns.clear();
				totoalTextViews = 0;
			}
		}
		/**
		 * 添加最后一行
		 */
		if (!childBtns.isEmpty()) {
			LinearLayout horizLL = new LinearLayout(context);
			horizLL.setOrientation(LinearLayout.HORIZONTAL);
			horizLL.setLayoutParams(layoutParams);

			for (TextView addBtn : childBtns) {
				horizLL.addView(addBtn);
			}
			parentLL.addView(horizLL);
			childBtns.clear();
			totoalTextViews = 0;
		}
	}

	public static void initlinearLayoutForSearchPeiyou(LinearLayout parentLL, String[] tags) {
		parentLL.removeAllViews();
		Context context=parentLL.getContext();
		/**
		 * 添加 TextView 的个数
		 */
		int textViewCount = tags.length;
		/**
		 * 每行的 LinearLayout
		 */
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 10, 0, 10);
		ArrayList<TextView> childBtns = new ArrayList<>();
		int totoalTextViews = 0;
		for (int i = 0; i < textViewCount; i++) {
			final int position=i;
			totoalTextViews++;
			String item = tags[i];
			if (tags.length==1&&item.length()==0){
				return;
			}
			int textWidth;
			textWidth=ScreenUtil.getTextViewLength(new TextView(context),item)+ScreenUtil.dip2px(context,20);
			/**
			 * 每个 item 的属性
			 */
			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(textWidth,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			itemParams.setMargins(0, 15, 15, 15);

			final TextView childTv =new TextView(context);
			childTv.setBackgroundResource(R.drawable.shape_solid_app_theme_app_green_corners5);
			childTv.setTextColor(parentLL.getResources().getColor(R.color.app_green));

			childTv.setGravity(Gravity.CENTER);
			childTv.setLayoutParams(itemParams);
			childTv.setPadding(10,5,10,5);
			childTv.setText(item);
			childTv.setTag(item);
			childTv.setTextSize(14);

			childBtns.add(childTv);

			if (totoalTextViews >= lines) {
				LinearLayout horizLL = new LinearLayout(context);
				horizLL.setOrientation(LinearLayout.HORIZONTAL);
				horizLL.setLayoutParams(layoutParams);

				for (TextView addBtn : childBtns) {
					horizLL.addView(addBtn);
				}
				parentLL.addView(horizLL);
				childBtns.clear();
				totoalTextViews = 0;
			}
		}
		/**
		 * 添加最后一行
		 */
		if (!childBtns.isEmpty()) {
			LinearLayout horizLL = new LinearLayout(context);
			horizLL.setOrientation(LinearLayout.HORIZONTAL);
			horizLL.setLayoutParams(layoutParams);

			for (TextView addBtn : childBtns) {
				horizLL.addView(addBtn);
			}
			parentLL.addView(horizLL);
			childBtns.clear();
			totoalTextViews = 0;
		}
	}

}
