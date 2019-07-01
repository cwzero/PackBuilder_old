
package com.liquidforte.packbuilder.curse.data;

import java.util.Date;
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
public class Download {
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	@JsonProperty("downloads")
	private long downloads;
	@JsonProperty("filesize")
	private String filesize;
	@JsonProperty("id")
	private long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;
	@JsonProperty("uploaded_at")
	private Date uploadedAt;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("versions")
	private List<String> versions = null;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Download() {
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
	public Download(long id, String url, String name, String type, String version, String filesize,
			List<String> versions, long downloads, Date uploadedAt) {
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
	public long getDownloads() {
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
	public Date getUploadedAt() {
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
	public List<String> getVersions() {
		return versions;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("downloads")
	public void setDownloads(long downloads) {
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
	public void setUploadedAt(Date uploadedAt) {
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
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

}