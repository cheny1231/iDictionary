package com.java.dictionary.net;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.java.dictionary.common.IMessage;
import com.java.dictionary.common.NetErrorMsg;
import com.java.dictionary.common.ResultMsg;
import com.java.dictionary.common.ServerErrorMsg;

/**
 * Internet Request
 * 
 * @param from
 *            source of translation
 * @param param
 *            params needed for request
 * @return result of request
 */

public class NetUtil {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 10000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	public static IMessage getResult(String baseUrl, String param, String method) {
		HttpURLConnection conn = null;
		try {
			URL url;
			if (method == METHOD_POST) {
				url = new URL(baseUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(DEF_CONN_TIMEOUT);
				conn.setRequestMethod(method);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
				printWriter.write(param);
				printWriter.flush();
			} else if (method == METHOD_GET) {
				url = new URL(baseUrl.concat("?").concat(param));
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(DEF_CONN_TIMEOUT);
				conn.setRequestMethod(method);
			}

			int statusCode = conn.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
				if(conn!=null)
					conn.disconnect();
				return new NetErrorMsg("网络未认证");
			}else if(statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
				if(conn!=null)
					conn.disconnect();
				return new ServerErrorMsg("服务器错误");
			}
			
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			close(os);
			close(is);
			conn.disconnect();
			String result = new String(os.toByteArray(), DEF_CHATSET);
			return new ResultMsg(result);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			if(conn!=null)
				conn.disconnect();
			return new NetErrorMsg("网络未连接");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
