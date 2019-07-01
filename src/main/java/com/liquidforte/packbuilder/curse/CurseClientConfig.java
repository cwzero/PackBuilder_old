package com.liquidforte.packbuilder.curse;

public class CurseClientConfig {
	private static final String API_ROOT_URL = "https://api.cfwidget.com/minecraft/";
	private static final String MODS_PATH = "mc-mods";
	private static final String ROOT_URL = "https://www.curseforge.com/minecraft/";
	private static final String CURSEFORGE_BASE_URL = "https://minecraft.curseforge.com/projects/";

	private String apiRootUrl = API_ROOT_URL;
	private String curseforgeBaseUrl = CURSEFORGE_BASE_URL;
	private String modsPath = MODS_PATH;
	private String rootUrl = ROOT_URL;
	
	public String getCurseforgeBaseUrl() {
		return curseforgeBaseUrl;
	}
	
	public String getCurseForgeUrl() {
		return getCurseforgeBaseUrl() + getModsPath();
	}
	
	public String getModsPath() {
		return modsPath;
	}
	
	public String getApiRootUrl() {
		return apiRootUrl;
	}
	
	public String getRootUrl() {
		return rootUrl;
	}
}
