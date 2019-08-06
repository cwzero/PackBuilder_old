package com.liquidforte.packbuilder.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.RedirectLocations;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;

public class DownloadHelper {
	public void download(String url, Path file) throws IOException {
		String originalLocation = url;

		HttpGet httpGet = new HttpGet(originalLocation);
		HttpContext context = new BasicHttpContext();
		HttpClient client = HttpClients.createDefault();
		try {
			client.execute(httpGet, context);
			RedirectLocations locations = (RedirectLocations) context.getAttribute("http.protocol.redirect-locations");
			URI result = new URI(locations.getAll().get(0).toString().replace(" ", "%20"));

			BufferedInputStream in = new BufferedInputStream(result.toURL().openStream());

			Files.copy(in, file);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
