package com.arogyak.pg2es.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.arogyak.pg2es.model.LatLon;
import com.arogyak.pg2es.model.NominatimPlace;

/**
 * Item reader that reads in Place information from nominatim DB and converts
 * them into a {@link Place} entity.
 * 
 * @author arogyakoirala
 *
 *
 */
@Component
//@JobScope
public class NominatimPlaceItemReader extends JdbcCursorItemReader<NominatimPlace> {

	/**
	 * Constructor based initialization
	 * 
	 * @param dataSource Data source
	 * @throws FileNotFoundException
	 * 
	 */

	private static Map<String, List<String>> getMapFromCSV(final String filePath) throws FileNotFoundException {

		Map<String, List<String>> map = new HashMap<>();
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineParts = line.split(",");

				List<String> labels = new ArrayList<String>();
				labels.add(lineParts[2]);
				labels.add(lineParts[3]);
				map.put(lineParts[0] + "_" + lineParts[1], labels);
			}
		}
//        System.out.println(map);

		return map;

	}

//	@Value("${config.amenity_mapping_csv}")
	private String amenityMappingCsvPath = "categories.csv";

	@Autowired
	public NominatimPlaceItemReader(DataSource dataSource) throws FileNotFoundException {
		this.setSql("select place_id,osm_id, osm_type, class, type, housenumber, "
				+ "get_address_by_language(place_id, NULL,  ARRAY['name:en', 'name', 'alt_name']) AS en_label,"
				+ "name," + "country_code AS country,"
				+ "case when GeometryType(geometry) = 'POINT' then ST_Y(geometry) else ST_Y(centroid) end as latitude,"
				+ "case when GeometryType(geometry) = 'POINT' then ST_X(geometry) else ST_X(centroid) end as longitude,"
				+ "rank_search, rank_address, " + "admin_level AS admin_level, " + "indexed_date AS indexed_date, "
				+ "GeometryType(geometry) AS geometry_type,  ST_AsGeoJSON(geometry) AS geometry,"
				+ "extratags, address " + "FROM placex");
		this.setRowMapper(new NominatimPlaceRowMapper(getMapFromCSV(amenityMappingCsvPath)));
//		System.out.println(amenityMappingCsvPath);
//		this.setRowMapper(new NominatimPlaceRowMapper());

		this.setDataSource(dataSource);

	}

	/**
	 * 
	 * Class that tells how to convert each ResultSet into a {@link Place} entity.
	 * 
	 * @author arogyakoirala
	 *
	 */
	private static class NominatimPlaceRowMapper implements RowMapper<NominatimPlace> {

//		private String asTag(String classification, String type) {
//			return classification + "|" + type;
//		}
//
//		public JSONObject toJsonObject(Map<String, String> addressMap) throws JSONException {
//			JSONObject jo = new JSONObject();
//
//			if (addressMap != null) {
//				for (Map.Entry<String, String> entry : addressMap.entrySet()) {
//					jo.put(entry.getKey(), (String) entry.getValue());
//				}
//			}
//
//			return jo;
//		}

		private Map<String, List<String>> mapping;
		private List<String> acceptableExtraTags;

		public NominatimPlaceRowMapper(Map<String, List<String>> mapping) {
			super();

			this.mapping = mapping;

			// TODO Auto-generated constructor stub
		}

		@Override
		public NominatimPlace mapRow(ResultSet rs, int rowNum) throws SQLException {

			final String UNKNOWN_TEXT = "Not known";
			final String FOOD_CATEGORY = "Places to eat / drink";
			final String SHOPS = "Shops";
			final String OTHER_COMMERCIAL_CATEGORY = "Commercial entities";

			NominatimPlace place = new NominatimPlace();

			place.setPlaceId(rs.getLong("place_id"));


			@SuppressWarnings("unchecked")
			Map<String, String> addressTags = (Map<String, String>) rs.getObject("address");
			place.setAddress(addressTags);

			String featureClass = rs.getString("class");
			String featureType = rs.getString("type");

			place.setFeatureClass(featureClass);
			place.setFeatureType(featureType);

			if (this.mapping.get(featureClass + "_" + featureType) != null) {
				List<String> categorizations = this.mapping.get(featureClass + "_" + featureType);
				if (categorizations.get(0) != "") {
					place.setAmenityCategory(categorizations.get(0));
				}

				if (categorizations.get(1) != "") {
					place.setAmenitySubCategory(categorizations.get(1));
				}
				
			}
			
			if(place.getAmenitySubCategory() == null) {
				place.setAmenitySubCategory(featureClass+"|"+featureType);
			}


			@SuppressWarnings("unchecked")
			Map<String, String> extraTags = (Map<String, String>) rs.getObject("extratags");
			if (extraTags != null) {

				if (extraTags.get("opening_hours") != null) {
					place.setOpeningHours(extraTags.get("opening_hours"));
				}

				if (extraTags.get("wheelchair") != null) {
					place.setWheelChair(extraTags.get("wheelchair"));
				} else {
					place.setWheelChair(UNKNOWN_TEXT);
				}

				if (extraTags.get("internet_access") != null) {
					place.setInternetAccess(extraTags.get("internet_access"));
				} else {
					place.setInternetAccess(UNKNOWN_TEXT);
				}

				if(place.getAmenityCategory() != null) {
					if (place.getAmenityCategory().equals(FOOD_CATEGORY)) {
						
						
						
						if (extraTags.get("opening_hours") == null) {
							place.setOpeningHours(UNKNOWN_TEXT);
						}
						
						if (extraTags.get("delivery") != null) {
							place.setDelivery(extraTags.get("delivery"));
						} else {
							place.setDelivery(UNKNOWN_TEXT);
						}
						
						if (extraTags.get("drive_through") != null) {
							place.setDriveThrough(extraTags.get("drive_through"));
						} else {
							place.setDriveThrough(UNKNOWN_TEXT);
						}
						
						if (extraTags.get("cuisine") != null) {
							place.setCuisine(extraTags.get("cuisine"));
						} else {
							place.setCuisine(UNKNOWN_TEXT);
						}
						
						if (extraTags.get("seating") != null) {
							place.setSeating(extraTags.get("seating"));
						} else {
							place.setSeating(UNKNOWN_TEXT);
						}
						
						if (extraTags.get("diet") != null) {
							place.setDiet(extraTags.get("diet"));
						} else {
							place.setDiet(UNKNOWN_TEXT);
						}
					}
					
					if (place.getAmenityCategory().equals(FOOD_CATEGORY)
							| place.getAmenityCategory().equals(OTHER_COMMERCIAL_CATEGORY)
							| place.getAmenityCategory().equals(SHOPS)) {
						
						if (extraTags.get("payment") != null) {
							place.setPaymentOptions(extraTags.get("payment"));
						} else {
							place.setPaymentOptions(UNKNOWN_TEXT);
						}
						
					}
				}


			}

			place.setDisplayName(rs.getString("en_label"));
			place.setLat(rs.getDouble("latitude"));
			place.setLicence(
					"Data \\u00a9 OpenStreetMap contributors, ODbL 1.0. https://www.openstreetmap.org/copyright");
			place.setLon(rs.getDouble("longitude"));
			place.setOsmId(rs.getLong("osm_id"));
			place.setOsmType(rs.getString("osm_type"));

			place.setCentroid(new LatLon(rs.getDouble("latitude"), rs.getDouble("longitude")));
			place.setAddressRank(rs.getInt("rank_address"));
			place.setSearchRank(rs.getInt("rank_search"));

			place.setGeometryType(rs.getString("geometry_type"));
			place.setGeometry(rs.getObject("geometry"));




			// begin setting name field
			String name = rs.getString("en_label").split(",", 2)[0].trim();



			if (name != null) {
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
			}

			place.setOnlyName(name);

			String[] addressPortion = rs.getString("en_label").split(",", 2);

			if (addressPortion.length > 1) {
				place.setOnlyAddress(rs.getString("en_label").split(",", 2)[1].trim());
			} else {
				place.setOnlyAddress(rs.getString("en_label"));
			}

			return place;

		}

	}
}
