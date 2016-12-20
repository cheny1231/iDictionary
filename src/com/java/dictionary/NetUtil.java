package com.java.dictionary;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;

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
//			if (to_url.contains("youdao")) {
//				Platform.runLater(new Runnable() {
//					@Override
//					public void run() {
//						new DialogueBox().displayNetUnconnected();
//					}
//				});
//			}
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
