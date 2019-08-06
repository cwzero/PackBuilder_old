package com.liquidforte.packbuilder.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.protocol.RedirectLocations;
import org.apache.hc.client5.http.utils.URIUtils;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ProtocolException;

public class RedirectHelper {
	private static class CustomRedirectStrategy extends DefaultRedirectStrategy {
		@Override
		protected URI createLocationURI(String location) throws ProtocolException {
			try {
				location = encodeUrl(location);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return super.createLocationURI(location);
		}

		private static String encodeUrl(String url) throws UnsupportedEncodingException {
			String prefix = url.substring(0, url.lastIndexOf('/') + 1);
			String suffix = url.substring(url.lastIndexOf('/') + 1);
			if (suffix.contains("%")) {
				return prefix + suffix;
			}
			String suffixEncoded = URLEncoder.encode(suffix, "UTF-8");
			return prefix + suffixEncoded;
		}
	}

	public static URL getRedirect(String originalLocation) throws IOException {
		HttpGet httpGet = new HttpGet(originalLocation);
		CloseableHttpClient client = HttpClients.custom().setRedirectStrategy(new CustomRedirectStrategy()).build();
		HttpClientContext context = HttpClientContext.create();

		client.execute(httpGet, context);
		RedirectLocations locations = (RedirectLocations) context.getAttribute("http.protocol.redirect-locations");

		try {
			URI location = URIUtils.resolve(httpGet.getUri(), new HttpHost("minecraft.curseforge.com"),
					locations.getAll());
			String uri = location.toString();

			uri = decodeUrl(uri);

			return new URL(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String decodeUrl(String url) throws UnsupportedEncodingException {
		String prefix = url.substring(0, url.lastIndexOf('/') + 1);
		String suffix = url.substring(url.lastIndexOf('/') + 1);
		String suffixDecoded = URLDecoder.decode(suffix, "UTF-8");
		String suffixReplace = suffixDecoded.replace(" ", "+");
		return prefix + suffixDecoded;
	}
}
