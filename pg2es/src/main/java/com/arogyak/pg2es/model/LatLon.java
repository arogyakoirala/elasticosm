package com.arogyak.pg2es.model;


public class LatLon {
	private Double lat;

	private Double lon;

	/**
	 * @param lat Latitude
	 * @param lon Longitude
	 */
	public LatLon(Double lat, Double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public Double getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}

	public LatLon() {
		super();
	}



}
