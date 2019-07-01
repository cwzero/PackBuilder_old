package com.liquidforte.packbuilder.curse.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "projectID", "fileID", "name" })
public class CurseFile {
	@JsonProperty("projectID")
	private long projectId;

	@JsonProperty("fileID")
	private long fileId;

	@JsonProperty("name")
	private String name = "";

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("projectID")
	public long getProjectId() {
		return projectId;
	}

	@JsonProperty("projectID")
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	@JsonProperty("fileID")
	public long getFileId() {
		return fileId;
	}

	@JsonProperty("fileID")
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
}
