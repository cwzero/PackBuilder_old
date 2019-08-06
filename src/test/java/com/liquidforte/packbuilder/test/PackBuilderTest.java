package com.liquidforte.packbuilder.test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.RedirectLocations;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liquidforte.packbuilder.curse.CurseClient;
import com.liquidforte.packbuilder.curse.CurseClientConfig;
import com.liquidforte.packbuilder.curse.data.CurseFile;
import com.liquidforte.packbuilder.inject.ClientConfigProvider;
import com.liquidforte.packbuilder.inject.JerseyClientProvider;
import com.liquidforte.packbuilder.inject.ObjectMapperProvider;
import com.liquidforte.packbuilder.util.DownloadHelper;

public class PackBuilderTest {
	private CurseClient curseClient;

	@BeforeEach
	public void setUp() {
		ObjectMapperProvider mapperProvider = new ObjectMapperProvider();
		ClientConfigProvider clientConfigProvider = new ClientConfigProvider();
		clientConfigProvider.setMapperProvider(mapperProvider);
		JerseyClientProvider clientProvider = new JerseyClientProvider();
		clientProvider.setClientConfig(clientConfigProvider.get());

		Client client = clientProvider.get();

		CurseClientConfig config = new CurseClientConfig();

		DownloadHelper downloadHelper = new DownloadHelper();

		curseClient = new CurseClient(client, config, downloadHelper);
	}

	@Test
	public void test() {
		CurseFile test = new CurseFile();
		test.setProjectId(32274);
		test.setFileId(2755458);
		try {
			curseClient.download(test, Paths.get("./mods"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
