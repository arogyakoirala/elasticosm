package com.arogyak.api;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.arogyak.api.model.Place;

@Repository
public interface PlaceRepository extends ElasticsearchRepository<Place, String>{



}
