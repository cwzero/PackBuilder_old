
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
@JsonPropertyOrder({ "href", "title" })
public class Link {

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	@JsonProperty("href")
	private String href;
	@JsonProperty("title")
	private String title;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Link() {
	}

	/**
	 *
	 * @param title
	 * @param href
	 */
	public Link(String href, String title) {
		super();
		this.href = href;
		this.title = title;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("href")
	public void setHref(String href) {
		this.href = href;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

}