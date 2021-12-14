package com.arogyak.api.model;

public class Aggregate {

	private String facet;
	private String label;
	private Long value;
	private Long error;
	public Aggregate(String facet, String label, Long value, Long error) {
		super();
		this.facet = facet;
		this.label = label;
		this.value = value;
		this.error = error;
	}
	public String getFacet() {
		return facet;
	}
	public void setFacet(String facet) {
		this.facet = facet;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Long getError() {
		return error;
	}
	public void setError(Long error) {
		this.error = error;
	}
	
	
}
