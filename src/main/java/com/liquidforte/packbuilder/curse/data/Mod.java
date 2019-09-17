package com.liquidforte.packbuilder.curse.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "url", "name", "type", "version", "filesize", "versions", "downloads", "uploaded_at" })
public class Mod {
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("downloads")
	private Downloads downloads;

	@JsonProperty("filesize")
	private String filesize;

	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("type")
	private String type;

	@JsonProperty("uploaded_at")
	private String uploadedAt;

	@JsonProperty("url")
	private String url;

	@JsonProperty("version")
	private String version;

	@JsonProperty("versions")
	private Map<String, List<File>> versions = new HashMap<>();
	
	@JsonProperty("files")
	private List<File> files = new ArrayList<>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Mod() {
	}

	/**
	 *
	 * @param id
	 * @param uploadedAt
	 * @param versions
	 * @param filesize
	 * @param name
	 * @param type
	 * @param downloads
	 * @param url
	 * @param version
	 */
	public Mod(long id, String url, String name, String type, String version, String filesize,
			Map<String, List<File>> versions, Downloads downloads, String uploadedAt) {
		super();
		this.id = id;
		this.url = url;
		this.name = name;
		this.type = type;
		this.version = version;
		this.filesize = filesize;
		this.versions = versions;
		this.downloads = downloads;
		this.uploadedAt = uploadedAt;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("downloads")
	public Downloads getDownloads() {
		return downloads;
	}

	@JsonProperty("filesize")
	public String getFilesize() {
		return filesize;
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("uploaded_at")
	public String getUploadedAt() {
		return uploadedAt;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@JsonProperty("versions")
	public Map<String, List<File>> getVersions() {
		return versions;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("downloads")
	public void setDownloads(Downloads downloads) {
		this.downloads = downloads;
	}

	@JsonProperty("filesize")
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	@JsonProperty("id")
	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("uploaded_at")
	public void setUploadedAt(String uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}

	@JsonProperty("versions")
	public void setVersions(Map<String, List<File>> versions) {
		this.versions = versions;
	}
	
	@JsonProperty("files")
	public List<File> getFiles() {
		return files;
	}
	
	@JsonProperty("files")
	public void setFiles(List<File> files) {
		this.files = files;
	}

	public File getLatestFile(String[] versions) {
		System.out.println("Getting latest file for " + getName());
		
		List<File> f = getFiles();
		
		f.sort((File a, File b) -> {
			return a.getUploadedAt().before(b.getUploadedAt()) ? 1 : -1;			
		});
		

		for (String version: versions) {
			for (File file: f) {
				if (file.getVersions().contains(version) && file.getType().equalsIgnoreCase("release")) {
					return file;
				}
			}
		}
		
		for (String version: versions) {
			if (!getVersions().containsKey(version)) {
				continue;
			} else {
				List<File> v = getVersions().get(version);

				v.sort((File a, File b) -> {
					return a.getUploadedAt().before(b.getUploadedAt()) ? 1 : -1;
				});

				return v.get(0);				
			}			
		}
		
		System.out.println("Error! Mod " +  getName() + " did not contain requested version. Available:");
		for (String version: getVersions().keySet()) {
			System.out.println("Version: \"" + version + "\"");
		}
		
		return null;
	}
}