package com.arogyak.api.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;



@Document(indexName = "place", createIndex = true)
public class Place {
	
	@Id
	private Long placeId;

	@Field(type = FieldType.Object)
	private Map<String, String> address;
	private List<Number> boundingbox;
	private String featureClass;
	private String displayName;
	private Double lat;
	private String licence;
	private Double lon;
	private Long osmId;
	private String osmWay;

	private String svg;
	private String featureType;

	private String osmType;

//	Additional fields
	@GeoPointField
	private LatLon centroid;
	private Integer addressRank;
	private Integer searchRank;
	private String geometryType;
	private Object geometry;
	
	@Field(type = FieldType.Object)
	private Map<String, String> extraTags;
	
	private String onlyName;
	private String onlyAddress;
	public Place() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Map<String, String> getAddress() {
		return address;
	}
	public void setAddress(Map<String, String> address) {
		this.address = address;
	}
	public List<Number> getBoundingbox() {
		return boundingbox;
	}
	public void setBoundingbox(List<Number> boundingbox) {
		this.boundingbox = boundingbox;
	}
	public String getFeatureClass() {
		return featureClass;
	}
	public void setFeatureClass(String featureClass) {
		this.featureClass = featureClass;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Long getOsmId() {
		return osmId;
	}
	public void setOsmId(Long osmId) {
		this.osmId = osmId;
	}
	public String getOsmWay() {
		return osmWay;
	}
	public void setOsmWay(String osmWay) {
		this.osmWay = osmWay;
	}
	public String getSvg() {
		return svg;
	}
	public void setSvg(String svg) {
		this.svg = svg;
	}
	public String getFeatureType() {
		return featureType;
	}
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}
	public String getOsmType() {
		return osmType;
	}
	public void setOsmType(String osmType) {
		this.osmType = osmType;
	}
	public LatLon getCentroid() {
		return centroid;
	}
	public void setCentroid(LatLon centroid) {
		this.centroid = centroid;
	}
	public Integer getAddressRank() {
		return addressRank;
	}
	public void setAddressRank(Integer addressRank) {
		this.addressRank = addressRank;
	}
	public Integer getSearchRank() {
		return searchRank;
	}
	public void setSearchRank(Integer searchRank) {
		this.searchRank = searchRank;
	}
	public String getGeometryType() {
		return geometryType;
	}
	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}
	public Object getGeometry() {
		return geometry;
	}
	public void setGeometry(Object geometry) {
		this.geometry = geometry;
	}
	public Map<String, String> getExtraTags() {
		return extraTags;
	}
	public void setExtraTags(Map<String, String> extraTags) {
		this.extraTags = extraTags;
	}
	public String getOnlyName() {
		return onlyName;
	}
	public void setOnlyName(String onlyName) {
		this.onlyName = onlyName;
	}
	public String getOnlyAddress() {
		return onlyAddress;
	}
	public void setOnlyAddress(String onlyAddress) {
		this.onlyAddress = onlyAddress;
	}
	
	

}
