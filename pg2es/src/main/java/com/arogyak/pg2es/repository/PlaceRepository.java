package com.arogyak.pg2es.repository;



//import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.arogyak.pg2es.model.Place;

//@Repository
public interface PlaceRepository extends ElasticsearchRepository<Place, Long> {
	/**
	 * @param osmId OSM ID of the place.
	 * @return {@link Place}
	 */
	public Place findByOsmId(String osmId);
//
	/**
	 * @param placeId Nominatim assigned Place ID of the place.
	 * @return {@link Place}
	 */
	public Place findByPlaceId(String placeId);
}
