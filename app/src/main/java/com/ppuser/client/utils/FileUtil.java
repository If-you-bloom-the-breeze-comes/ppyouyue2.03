package com.ppuser.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import com.ppuser.client.common.CommonData;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 文件相关工具类
 * 
 * @author huangfucai
 *
 */
@SuppressLint("Recycle")
public class FileUtil {

	/**
	 * 通过流下载保存文件
	 * 
	 * @param is 需要保存的流
	 * @param file 保存到哪个文件夹
	 * @param name 需要保存的文件名称
	 */
	public static void saveFile(InputStream is, File file, String name) {
		boolean saveFinish = false;
		if (is == null) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file + name);
			byte[] buff = new byte[1024];
			do {
				int numRead = is.read(buff);
				if (numRead <= 0) {
					saveFinish = true;
					break;
				}
				fos.write(buff, 0, numRead);
			} while (!saveFinish);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过流下载保存文件 有回调
	 * 
	 * @param is 需要保存的流
	 * @param file 保存到哪个文件夹
	 * @param name 需要保存的文件名称
	 */
	public static void saveFileForHandler(InputStream is, File file, String name, Handler mHandler) {
		boolean saveFinish = false;
		if (is == null) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file + name);
			byte[] buff = new byte[1024];
			do {
				int numRead = is.read(buff);
				if (numRead <= 0) {
					saveFinish = true;
					break;
				}
				fos.write(buff, 0, numRead);
			} while (!saveFinish);
			Message message = new Message();
			Bundle b = new Bundle();
			b.putString("path", file + name);
			message.setData(b);
			message.what = 0;
			mHandler.sendMessage(message);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过流获取文本里的文字
	 * 
	 * @param is
	 * @return
	 */
	public static String getString(InputStream is) {
		String str = null;
		BufferedReader reader = null;
		StringBuilder strBuilder = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while (null != (line = reader.readLine()))
				strBuilder.append(line);
			str = strBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @param isDeleteDirectory 是否删除目录，true表示删除，false表示不删除
	 * @return
	 */
	public static boolean delete(File file, boolean isDeleteDirectory) {
		if (!file.canWrite()) {
			return false;
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				delete(f,isDeleteDirectory);
			}
		}
		return isDeleteDirectory?file.delete():file.isDirectory()?false:file.delete();
	}

	/**
	 * 创建文件，默认在项目存储的根目录
	 *
	 * @param fileName
	 * @return
	 * @throws Exception
     */
	public static File createSDFile(String fileName) throws Exception {
		File file = new File(CommonData.STORAGE_ROOT + fileName);
		if (isFileExist(fileName)) {
			file.delete();
		}
		file.createNewFile();
		return file;

	}

	/**
	 * 判断文件是否存在
	 *
	 * @param fileName
	 * @return
     */
	public static boolean isFileExist(String fileName) {
		File file = new File(CommonData.STORAGE_ROOT + fileName);
		return file.exists();
	}

	/**
	 * 创建文件夹
	 *
	 * @param dirName
	 * @return
	 * @throws Exception
     */
	public static File createSDDir(String dirName) throws Exception {
		File dir = new File(CommonData.STORAGE_ROOT + dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 创建没有存在的文件夹
	 *
	 * @param dirName1
	 * @param dirName2
	 * @return
	 * @throws Exception
     */
	public static File createSDDirIsNotExists(String dirName1, String dirName2) throws Exception {
		File dir1 = new File(CommonData.STORAGE_ROOT + "/" + dirName1);
		File dir2 = new File(CommonData.STORAGE_ROOT + "/" + dirName1 + "/" + dirName2);
		if (!dir1.exists()) {
			dir1.mkdir();
		}
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		return dir2;
	}

	/**
	 * 通过url获取字符串
	 *
	 * @param urlStr
	 * @param charSet
     * @return
     */
	public static String getText(String urlStr, String charSet) {
		StringBuffer result = new StringBuffer();
		String temp = null;
		BufferedReader reader = null;
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
			if (httpUrl.getResponseCode() == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), charSet));
				while ((temp = reader.readLine()) != null) {
					result.append(temp);
				}
			} else {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 通过url获取字符串（换行）
	 *
	 * @param urlStr
	 * @param charSet
     * @return
     */
	public static String getTextByline(String urlStr, String charSet) {
		StringBuffer result = new StringBuffer();
		String temp = null;
		BufferedReader reader = null;
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
			reader = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), charSet));
			while ((temp = reader.readLine()) != null) {
				result.append(temp + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 将流写到sd卡
	 *
	 * @param path
	 * @param fileName
	 * @param input
     * @return
     */
	public static File writeToSD(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream out = null;
		try {
			createSDDir(path);
			file = createSDFile(path + "/" + fileName);
			out = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while (input.read(buffer) != -1) {
				out.write(buffer);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 将文字写到sd卡
	 *
	 * @param path
	 * @param fileName
	 * @param str
     * @return
     */
	public static File writeToSD(String path, String fileName, String str) {
		File file = null;
		OutputStream out = null;
		try {
			createSDDir(path);
			file = createSDFile(path + "/" + fileName);
			out = new FileOutputStream(file);
			out.write(str.getBytes());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 将流写到文件
	 *
	 * @param file
	 * @param input
     * @return
     */
	public static File writeToSD(File file, InputStream input) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while (input.read(buffer) != -1) {
				out.write(buffer);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	/**
	 * 将file转换成二进制数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getFileToByte(File file) {
		byte[] fileByte = new byte[(int) file.length()];
		InputStream is=null;
		try {
			is= new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			byte[] bb = new byte[2048];
			int ch;
			ch = is.read(bb);
			while (ch != -1) {
				bytestream.write(bb, 0, ch);
				ch = is.read(bb);
			}
			fileByte = bytestream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileByte;
	}

	/**
	 * 下载获取文件流
	 * 
	 * @param urlStr
	 * @return
	 */
	public static InputStream getInputStream(String urlStr) {
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
			return httpUrl.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @param isOver
	 * @return
	 */
	public static int download(String urlStr, String path, String fileName, boolean isOver) {
		InputStream input = null;
		try {
			if (isFileExist(fileName) && !isOver) {
				return 1;
			} else {
				input = getInputStream(urlStr);
				if (input != null) {
					File file = writeToSD(path, fileName, input);
					if (file != null) {
						return 0;
					} else {
						return -1;
					}
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delete(String path) {
		File file = new File(path);
		try {
			if (file.exists()) {
				return file.delete();
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String read(String path, String charset) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(CommonData.STORAGE_ROOT + path)), Charset.forName(charset)));
		StringBuilder result = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
		} finally {
			reader.close();
		}
		return result.toString();
	}

	public static String readFirstLine(String path, String charset) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(CommonData.STORAGE_ROOT + path)), Charset.forName(charset)));
		StringBuilder result = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
				return result.toString();
			}
		} finally {
			reader.close();
		}
		return result.toString();
	}

	/**
	 * 获取文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception {
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {
				fis = new FileInputStream(f);
				s = fis.available();
			} else {
				f.createNewFile();
			}
		} finally {
			fis.close();
		}
		return s;
	}

	/**
	 * 判断sd卡是否存在
	 * 
	 * @return
	 */
	public static boolean checkSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static void createDirectory(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}



	/**
	 * 文件下载
	 * 
	 * @param url
	 * @param file1
	 * @param file2
	 * @param name
	 */
	public static void fileDown(final String url, final String file1, final String file2,
                                final String name) {
		new Thread() {
			@Override
			public void run() {
				try {
					FileUtil.saveFile(FileUtil.getInputStream(url),
							FileUtil.createSDDirIsNotExists(file1, file2), name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 文件下载，有回调
	 * 
	 * @param url
	 * @param file1
	 * @param file2
	 * @param name
	 */
	public static void fileDownForHandler(final String url, final String file1, final String file2,
                                          final String name, final Handler myHandler) {
		new Thread() {
			@Override
			public void run() {
				try {
					FileUtil.saveFileForHandler(FileUtil.getInputStream(url),
							FileUtil.createSDDirIsNotExists(file1, file2), name, myHandler);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 删除文件
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @return boolean 存储成功的标志
	 */
	public static boolean deleteFile(Context context, String fileName) {
		return context.deleteFile(fileName);
	}

	/**
	 * 文件是否存在
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean exists(Context context, String fileName) {
		return new File(context.getFilesDir(), fileName).exists();
	}

	/**
	 * 存储文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeFile(Context context, String fileName, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 存储文本数据
	 * 
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeFile(String filePath, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(Context context, String fileName) {
		if (!exists(context, fileName)) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * 
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(String filePath) {
		if (filePath == null || !new File(filePath).exists()) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = new FileInputStream(filePath);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readAssets(Context context, String fileName) {
		InputStream is = null;
		String content = null;
		try {
			is = context.getAssets().open(fileName);
			if (is != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = is.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				is.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 存储单个Parcelable对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param parcelObject
	 *            对象必须实现Parcelable
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeParcelable(Context context, String fileName, Parcelable parcelObject) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			Parcel parcel = Parcel.obtain();
			parcel.writeParcelable(parcelObject, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
			byte[] data = parcel.marshall();
			fos.write(data);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 存储List对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名，要在系统内保持唯一
	 * @param list
	 *            对象数组集合，对象必须实现Parcelable
	 * @return boolean 存储成功的标志
	 */
	public static boolean writeParcelableList(Context context, String fileName, List<Parcelable> list) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			if (list instanceof List) {
				fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				Parcel parcel = Parcel.obtain();
				parcel.writeList(list);
				byte[] data = parcel.marshall();
				fos.write(data);

				success = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 读取单个数据对象
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return Parcelable, 读取到的Parcelable对象，失败返回null
	 */
	public static Parcelable readParcelable(Context context, String fileName, ClassLoader classLoader) {
		Parcelable parcelable = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				parcelable = parcel.readParcelable(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			parcelable = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return parcelable;
	}

	/**
	 * 读取数据对象列表
	 * 
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return List, 读取到的对象数组，失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static List<Parcelable> readParcelableList(Context context, String fileName, ClassLoader classLoader) {
		List<Parcelable> results = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				results = parcel.readArrayList(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			results = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return results;
	}

	public static boolean saveSerializable(Context context, String fileName, Serializable data) {
		boolean success = false;
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
			oos.writeObject(data);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	public static Serializable readSerialLizable(Context context, String fileName) {
		Serializable data = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(context.openFileInput(fileName));
			data = (Serializable) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}

	/**
	 * 从assets里边读取字符串
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 * @param dstFile
	 * @return
	 */
	public static boolean copy(String srcFile, String dstFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {

			File dst = new File(dstFile);
			if (!dst.getParentFile().exists()) {
				dst.getParentFile().mkdirs();
			}

			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(dstFile);

			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return true;
	}

}
