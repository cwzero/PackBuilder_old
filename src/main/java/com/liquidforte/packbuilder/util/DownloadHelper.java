package com.liquidforte.packbuilder.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.net.ssl.HttpsURLConnection;

public class DownloadHelper {
	public void download(String url, Path file) throws IOException {
		String originalLocation = url.replace(' ', '+');

		HttpsURLConnection conn = (HttpsURLConnection) new URL(originalLocation).openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.connect();

		String newLocation = conn.getHeaderField("Location").replace(' ', '+');

		conn = (HttpsURLConnection) new URL(newLocation).openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.connect();

		Files.copy(conn.getInputStream(), file);
	}
}
