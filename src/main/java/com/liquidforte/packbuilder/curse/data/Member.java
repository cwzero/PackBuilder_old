
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
@JsonPropertyOrder({ "title", "username" })
public class Member {

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	@JsonProperty("title")
	private String title;
	@JsonProperty("username")
	private String username;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Member() {
	}

	/**
	 * 
	 * @param username
	 * @param title
	 */
	public Member(String title, String username) {
		super();
		this.title = title;
		this.username = username;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

}