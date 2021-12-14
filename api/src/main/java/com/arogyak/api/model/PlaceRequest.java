package com.arogyak.api.model;

public class PlaceRequest {

	private String q;
	private Bounds bounds;
	
	
	
	public PlaceRequest(String q, Bounds bounds) {
		super();
		this.q = q;
		this.bounds = bounds;
	}
	
	
	public PlaceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Bounds getBounds() {
		return bounds;
	}
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
	
	
	
}
