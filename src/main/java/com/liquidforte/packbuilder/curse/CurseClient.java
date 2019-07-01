package com.liquidforte.packbuilder.curse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.liquidforte.packbuilder.curse.data.CurseFile;
import com.liquidforte.packbuilder.curse.data.Mod;

public class CurseClient {
	private Client client;

	private CurseClientConfig config;
	private WebTarget apiRoot;
	private WebTarget apiMods;
	private WebTarget mods;
	private WebTarget root;

	public CurseClient() {

	}

	public CurseClient(CurseClientConfig config) {
		this();
		this.config = config;
	}

	public CurseClient(Client client) {
		this();
		this.client = client;
	}

	@Inject
	public CurseClient(Client client, CurseClientConfig config) {
		this(client);
		this.config = config;
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
		return this.config.getCurseforgeBaseUrl() + projectId + "?cookieTest=1";
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

		long fileId = result.getLatestFile("1.12.2").getId();

		return getFileName(projectId, fileId);
	}

	public WebTarget getLatestFileTarget(String name) throws IOException {
		long projectId = getProjectId(name);
		Mod result = getMod(name);

		long fileId = result.getLatestFile("1.12.2").getId();

		return getFileTarget(projectId, fileId);
	}

	public long getLatestFileId(String name) {
		Mod result = getMod(name);
		return result.getLatestFile("1.12.2").getId();
	}

	public String[] getLocation(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.connect();

		return conn.getHeaderField("Location").split("/");
	}

	public String getFileName(long projectId, long fileId) throws IOException {
		String[] path = getLocation(getFileTarget(projectId, fileId).getUri().toURL());
		return path[path.length - 1];
	}

	public String getProjectName(long projectId) throws IOException {
		URL url = new URL(getProjectUrl(projectId));
		String[] path = getLocation(url);

		String name = path[5];
		name = name.substring(0, name.indexOf("?cookieTest=1"));
		return name;
	}

	public CurseFile update(CurseFile input) throws IOException {
		String name = getProjectName(input.getProjectId());

		CurseFile output = new CurseFile();
		output.setProjectId(input.getProjectId());
		output.setFileId(getLatestFileId(name));
		output.setName(name);

		return output;
	}

	public Path getDestination(CurseFile input, Path modsDir) throws IOException {
		String fileName = getFileName(input.getProjectId(), input.getFileId());
		return modsDir.resolve(fileName);
	}

	public void download(CurseFile input, Path modsDir) throws IOException {
		if (!modsDir.toFile().exists() || !modsDir.toFile().isDirectory()) {
			modsDir.toFile().mkdirs();
		}
		Path path = getDestination(input, modsDir);

		Mod mod = getMod(getProjectName(input.getProjectId()));
		WebTarget target = getLatestFileTarget(mod.getName());

		Response response = target.request().get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			InputStream in = response.readEntity(InputStream.class);
			Files.copy(in, path);
		}
	}
}