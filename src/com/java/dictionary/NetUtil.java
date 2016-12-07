package com.java.dictionary;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetUtil {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 10000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static final String METHOD_GET = "GET";

	/**
	 * 网络请求部分
	 * 
	 * @param from
	 *            获取翻译的源
	 * @param param
	 *            请求所需的参数
	 * @return 返回请求结果字符串
	 */
	public static String getResult(String to_url) {
		try {
			HttpURLConnection conn = null;
			URL url = new URL(to_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setRequestMethod(METHOD_GET);

			int statusCode = conn.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				return "error";
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
			return result;

		} catch (MalformedURLException e) {
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
