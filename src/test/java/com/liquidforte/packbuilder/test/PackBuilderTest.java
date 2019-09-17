package com.liquidforte.packbuilder.test;

import java.io.IOException;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;

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

	@Test
	public void testEncoding() {
		CurseFile bibliocraft = new CurseFile();
		bibliocraft.setProjectId(228027);
		bibliocraft.setFileId(2574880);

		CurseFile harvestcraft = new CurseFile();
		harvestcraft.setProjectId(221857);
		harvestcraft.setFileId(2717443);

		try {
			curseClient.download(bibliocraft, Paths.get("./mods"));
			curseClient.download(harvestcraft, Paths.get("./mods"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFailure() {
		CurseFile journeymap = new CurseFile();
		journeymap.setProjectId(32274);
		journeymap.setFileId(2755458);
		
		CurseFile wands = new CurseFile();
		wands.setProjectId(235595);
		wands.setFileId(2705633);

		try {
			curseClient.download(journeymap, Paths.get("./mods"));
			curseClient.download(wands, Paths.get("./mods"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
