
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
@JsonPropertyOrder({ "total", "monthly" })
public class Downloads {

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	@JsonProperty("monthly")
	private long monthly;
	@JsonProperty("total")
	private long total;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Downloads() {
	}

	/**
	 *
	 * @param total
	 * @param monthly
	 */
	public Downloads(long total, long monthly) {
		super();
		this.total = total;
		this.monthly = monthly;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("monthly")
	public long getMonthly() {
		return monthly;
	}

	@JsonProperty("total")
	public long getTotal() {
		return total;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("monthly")
	public void setMonthly(long monthly) {
		this.monthly = monthly;
	}

	@JsonProperty("total")
	public void setTotal(long total) {
		this.total = total;
	}

}