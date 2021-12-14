package com.arogyak.pg2es.repository;



//import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.arogyak.pg2es.model.NominatimPlace;

//@Repository
public interface PlaceRepository extends ElasticsearchRepository<NominatimPlace, Long> {
	/**
	 * @param osmId OSM ID of the place.
	 * @return {@link Place}
	 */
	public NominatimPlace findByOsmId(String osmId);
//
	/**
	 * @param placeId Nominatim assigned Place ID of the place.
	 * @return {@link Place}
	 */
	public NominatimPlace findByPlaceId(String placeId);
}
