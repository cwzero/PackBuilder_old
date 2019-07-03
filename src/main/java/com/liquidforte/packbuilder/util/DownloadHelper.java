package com.liquidforte.packbuilder.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.net.ssl.HttpsURLConnection;

public class DownloadHelper {
	public void download(String url, Path file) throws IOException {
		String originalLocation = url;

		HttpsURLConnection conn = (HttpsURLConnection) new URL(originalLocation).openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.connect();

		String newLocation = conn.getHeaderField("Location").replace(" ", "%20");

		BufferedInputStream in = new BufferedInputStream(new URL(newLocation).openStream());

		Files.copy(in, file);
	}
}
