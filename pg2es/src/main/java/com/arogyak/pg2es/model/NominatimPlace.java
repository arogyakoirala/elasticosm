package com.arogyak.pg2es.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.Setting;



@Document(indexName = "place", createIndex = true)
@Setting(settingPath = "settings.json")
public class NominatimPlace {

	@Id
	@org.springframework.data.annotation.Id
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

	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true),
			@InnerField(suffix = "shingle", type = FieldType.Text, store = true, analyzer = "shingle", searchAnalyzer = "search"),
			@InnerField(suffix = "engram", type = FieldType.Text, store = true, analyzer = "e_ngram", searchAnalyzer = "search") })
	private String onlyName;
	
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true),
			@InnerField(suffix = "engram", type = FieldType.Text, store = true, analyzer = "e_ngram", searchAnalyzer = "search") })
	private String onlyAddress;

	private String amenityCategory;

	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true),
			@InnerField(suffix = "synonym", type = FieldType.Text, store = true, searchAnalyzer = "synonym"),
			@InnerField(suffix = "stem", type = FieldType.Text, store = true, analyzer = "stemmer") })
	private String amenitySubCategory;

	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String openingHours;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String wheelChair;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String internetAccess;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String delivery;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String driveThrough;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String paymentOptions;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String takeaway;

	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String cuisine;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String seating;
	@MultiField(mainField = @Field(type = FieldType.Text, store = true), otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword, store = true) })
	private String diet;
	

	public NominatimPlace() {
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

	public String getAmenityCategory() {
		return amenityCategory;
	}

	public void setAmenityCategory(String amenityCategory) {
		this.amenityCategory = amenityCategory;
	}

	public String getAmenitySubCategory() {
		return amenitySubCategory;
	}

	public void setAmenitySubCategory(String amenitySubCategory) {
		this.amenitySubCategory = amenitySubCategory;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getWheelChair() {
		return wheelChair;
	}

	public void setWheelChair(String wheelChair) {
		this.wheelChair = wheelChair;
	}

	public String getInternetAccess() {
		return internetAccess;
	}

	public void setInternetAccess(String internetAccess) {
		this.internetAccess = internetAccess;
	}
	
	

	public String getPaymentOptions() {
		return paymentOptions;
	}

	public void setPaymentOptions(String paymentOptions) {
		this.paymentOptions = paymentOptions;
	}

	
	
	
	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getDriveThrough() {
		return driveThrough;
	}

	public void setDriveThrough(String driveThrough) {
		this.driveThrough = driveThrough;
	}

	public String getTakeaway() {
		return takeaway;
	}

	public void setTakeaway(String takeaway) {
		this.takeaway = takeaway;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getSeating() {
		return seating;
	}

	public void setSeating(String seating) {
		this.seating = seating;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}
	
	
	

}
