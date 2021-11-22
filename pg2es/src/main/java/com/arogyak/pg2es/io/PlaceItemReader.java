package com.arogyak.pg2es.io;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.arogyak.pg2es.model.LatLon;
import com.arogyak.pg2es.model.Place;



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
public class PlaceItemReader extends JdbcCursorItemReader<Place> {

	/**
	 * Constructor based initialization
	 * 
	 * @param dataSource Data source
	 */
	@Autowired
	public PlaceItemReader(DataSource dataSource) {
		this.setSql("select place_id,osm_id, osm_type, class, type, housenumber, "
				+ "get_address_by_language(place_id, NULL,  ARRAY['name:en', 'name', 'alt_name']) AS en_label,"
				+ "name," + "country_code AS country,"
				+ "case when GeometryType(geometry) = 'POINT' then ST_Y(geometry) else ST_Y(centroid) end as latitude,"
				+ "case when GeometryType(geometry) = 'POINT' then ST_X(geometry) else ST_X(centroid) end as longitude,"
				+ "rank_search, rank_address, " + "admin_level AS admin_level, " + "indexed_date AS indexed_date, "
				+ "GeometryType(geometry) AS geometry_type,  ST_AsGeoJSON(geometry) AS geometry," + "extratags "
				+ "FROM placex where not (name is null)");
		this.setRowMapper(new PlaceRowMapper());
		this.setDataSource(dataSource);
	}

	/**
	 * 
	 * Class that tells how to convert each ResultSet into a {@link Place} entity.
	 * 
	 * @author arogyakoirala
	 *
	 */
	private static class PlaceRowMapper implements RowMapper<Place> {

		private String asTag(String classification, String type) {
			return classification + "|" + type;
		}

		@Override
		public Place mapRow(ResultSet rs, int rowNum) throws SQLException {

			Place place = new Place();

			String[] addressPortion = rs.getString("en_label").split(",", 2);

			place.setPlaceId(rs.getLong("place_id"));
			place.setOsmId(rs.getLong("osm_id"));
			place.setOsmType(rs.getString("osm_type"));
			place.setHouseNumber(rs.getString("housenumber"));

			if (addressPortion.length > 1) {
				place.setAddress(rs.getString("en_label").split(",", 2)[1].trim());
			} else {
				place.setAddress(rs.getString("en_label"));
			}

			place.setClassification(rs.getString("class"));
			place.setType(rs.getString("type"));
			place.setAddressRank(rs.getInt("rank_address"));
			place.setSearchRank(rs.getInt("rank_search"));
			place.setCentroid(new LatLon(rs.getDouble("latitude"), rs.getDouble("longitude")));
			place.setGeometryType(rs.getString("geometry_type"));
			place.setGeometry(rs.getObject("geometry"));

			// begin setting tags field
			List<String> extraTags = new ArrayList<String>();

			Map<String, String> extraTagsField = (Map<String, String>) rs.getObject("extratags");
			if (extraTagsField != null) {
				for (Map.Entry<String, String> entry : extraTagsField.entrySet()) {
					extraTags.add(asTag(entry.getKey(), entry.getValue()));
				}
			}
			List<String> tags = Stream
					.concat(extraTags.stream(), Arrays.asList(asTag(rs.getString(4), rs.getString(5))).stream())
					.collect(Collectors.toList());

			place.setTags(tags);
			// end setting tags field

			place.setCountry(rs.getString("country"));
			place.setAdminLevel(rs.getInt("admin_level"));

			// begin setting name field
			Map<String, String> nameField = (Map<String, String>) rs.getObject("name");
			String name = rs.getString("en_label").split(",", 2)[0].trim();

			if (name != null) {
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
			}

			place.setName(name);
			
//			System.out.println(place.getName());

			// end setting name field

			// begin setting processedName, and nameAddressRaw field
//			if (name != null && !name.isEmpty()) {
//				String processedName = rs.getString("en_label").toLowerCase();
//				place.setNameAddressRaw(processedName.replace(",", " "));
//				processedName = processedName.replaceAll("\\s+", "").replace(",", "");
//				place.setProcessedName(processedName);
//			}

			return place;

		}

	}
}
