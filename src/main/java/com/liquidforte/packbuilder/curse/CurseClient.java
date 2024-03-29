package com.liquidforte.packbuilder.curse;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.liquidforte.packbuilder.curse.data.CurseFile;
import com.liquidforte.packbuilder.curse.data.File;
import com.liquidforte.packbuilder.curse.data.Mod;
import com.liquidforte.packbuilder.util.DownloadHelper;
import com.liquidforte.packbuilder.util.RedirectHelper;

public class CurseClient {
	private Client client;

	private CurseClientConfig config;
	private WebTarget apiRoot;
	private WebTarget apiMods;
	private WebTarget mods;
	private WebTarget root;

	private String[] versions = { "1.12.2", "1.12.1", "1.12" };

	private DownloadHelper downloadHelper;

	@Inject
	public CurseClient(Client client, CurseClientConfig config, DownloadHelper downloadHelper) {
		this.client = client;
		this.config = config;
		this.downloadHelper = downloadHelper;
	}

	public Client getClient() {
		return client;
	}

	public CurseClientConfig getConfig() {
		return config;
	}

	public WebTarget getApiModsTarget() {
		if (apiMods == null) {
			apiMods = getApiRoot().path(config.getModsPath());
		}
		return apiMods;
	}

	public WebTarget getModsTarget() {
		if (mods == null) {
			mods = getRootTarget().path(config.getModsPath());
		}
		return mods;
	}

	public WebTarget getApiModTarget(String modId) {
		return getApiModsTarget().path(modId);
	}

	public WebTarget getModTarget(String modId) {
		return getModsTarget().path(modId);
	}

	public WebTarget getFileTarget(long projectId, long fileId) throws IOException {
		String name = getProjectName(projectId);
		return getModTarget(name).path("download").path("" + fileId).path("file");
	}

	public File getFile(long projectId, long fileId) throws IOException {
		String name = getProjectName(projectId);
		Mod mod = getMod(name);
		for (File file : mod.getFiles()) {
			if (file.getId() == fileId) {
				return file;
			}
		}
		return null;
	}

	public long getFileSize(long projectId, long fileId) throws IOException {
		File file = getFile(projectId, fileId);
		return file.getFilesize();
	}

	public WebTarget getApiRoot() {
		if (apiRoot == null) {
			apiRoot = getClient().target(config.getApiRootUrl());
		}
		return apiRoot;
	}

	public WebTarget getRootTarget() {
		if (root == null) {
			root = getClient().target(config.getRootUrl());
		}
		return root;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setConfig(CurseClientConfig config) {
		this.config = config;
	}

	public String getProjectUrl(long projectId) {
		return this.config.getCurseforgeBaseUrl() + projectId;
	}

	public Mod getMod(String name) {
		Invocation invocation = getApiModTarget(name).request().buildGet();
		Response response = invocation.invoke();

		Mod result = response.readEntity(Mod.class);
		result.setName(name);
		return result;
	}

	public long getProjectId(String name) {
		return getMod(name).getId();
	}

	public String getLatestFileName(String name) throws IOException {
		long projectId = getProjectId(name);
		Mod result = getMod(name);

		long fileId = result.getLatestFile(versions).getId();

		return getFileName(projectId, fileId);
	}

	public WebTarget getLatestFileTarget(String name) throws IOException {
		long projectId = getProjectId(name);
		Mod result = getMod(name);

		long fileId = result.getLatestFile(versions).getId();

		return getFileTarget(projectId, fileId);
	}

	public long getLatestFileId(String name) {
		Mod result = getMod(name);
		File file = result.getLatestFile(versions);
		return file.getId();
	}

	public String[] getLocation(URL url) throws IOException {
		try {
			URL result = RedirectHelper.getRedirect(url.toString());

			return result.toString().split("/");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	public String getFileName(long projectId, long fileId) throws IOException {
		String[] path = getLocation(getFileTarget(projectId, fileId).getUri().toURL());
		return path[path.length - 1].replace("+", " ");
	}

	public String getProjectName(long projectId) throws IOException {
		URL url = new URL(getProjectUrl(projectId));
		String[] path = getLocation(url);

		String name = path[5];
		return name;
	}

	public CurseFile update(CurseFile input) throws IOException {
		String name = getProjectName(input.getProjectId());
		input.setName(name);

		long fileId = getLatestFileId(name);

		CurseFile output = new CurseFile();
		output.setProjectId(input.getProjectId());
		output.setFileId(fileId);
		output.setName(name);

		return output;
	}

	public Path getDestination(CurseFile input, Path modsDir) throws IOException {
		String fileName = getFileName(input.getProjectId(), input.getFileId());
		return modsDir.resolve(fileName);
	}

	public void download(CurseFile input, Path modsDir) throws IOException {
		if (input.getName() == null) {
			String name = getProjectName(input.getProjectId());
			input.setName(name);
		}

		Path path = getDestination(input, modsDir);
		
		if (path.toFile().exists() && path.toFile().length() != getFileSize(input.getProjectId(), input.getFileId())) {
			path.toFile().delete();
		}
		
		if (path.toFile().exists() && path.toFile().length() == getFileSize(input.getProjectId(), input.getFileId())) {
			return;
		}

		String uri = "https://www.curseforge.com/minecraft/mc-mods/" + input.getProjectId() + "/download/"
				+ input.getFileId() + "/file";

		System.out.println("Downloading " + input.getName());
		downloadHelper.download(uri, path);
	}
}