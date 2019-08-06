package com.liquidforte.packbuilder.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadHelper {
	public void download(String url, Path file) throws IOException {
		try {
			URL result = RedirectHelper.getRedirect(url);

			BufferedInputStream in = new BufferedInputStream(result.openStream());

			Files.copy(in, file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
