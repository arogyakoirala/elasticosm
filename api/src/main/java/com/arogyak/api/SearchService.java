package com.arogyak.api;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.arogyak.api.model.Place;

@Service
public class SearchService {

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public List<Place> findPlace(String q) {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(q);

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

		SearchHits<Place> placeHits = elasticsearchOperations.search(searchQuery, Place.class,
				IndexCoordinates.of("place"));

		List<Place> placeMatches = new ArrayList<Place>();
		placeHits.forEach(searchHit -> {
			placeMatches.add(searchHit.getContent());
		});
		return placeMatches;

	}

}
