package com.arogyak.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.clients.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;

import com.arogyak.api.model.Aggregate;
import com.arogyak.api.model.Place;
import com.arogyak.api.model.PlacesResponse;

@Service
public class SearchService {

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	private void addAggregates(String column, Aggregations aggregations, List<Aggregate> aggs) {

		Terms results = aggregations.get(column);
		for (Terms.Bucket bucket : results.getBuckets()) {
			aggs.add(new Aggregate(column, bucket.getKeyAsString(), bucket.getDocCount(), bucket.getDocCountError()));
		}

	}

	public PlacesResponse findPlace(String q, Double top, Double left, Double bottom, Double right) {

		String processedQ = q.trim();

		List<Aggregate> allAggregations = new ArrayList<Aggregate>();

		;
//		Query specification
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("onlyName.shingle", processedQ).fuzziness(Fuzziness.ONE)
						.prefixLength(2).boost(1.0f))
				.should(QueryBuilders.matchQuery("amenitySubCategory.synonym", q).fuzziness(Fuzziness.ONE)
						.prefixLength(2).boost(5.0f))
				.should(QueryBuilders.matchQuery("amenitySubCategory.stem", q).fuzziness(Fuzziness.ONE).prefixLength(2)
						.boost(1.0f))
				.should(QueryBuilders.matchQuery("onlyName.engram", processedQ).boost(1.0f))
				.should(QueryBuilders.matchQuery("onlyName.engram", processedQ).boost(1.0f)))
//				
//				.should(QueryBuilders.matchQuery("onlyName.shingle", processedQ).boost(2.5f))
				.must(QueryBuilders.geoBoundingBoxQuery("centroid").setCorners(top, left, bottom, right).boost(1.0f));

//				.should(QueryBuilders.matchQuery("amenitySubCategory.synonym", q).prefixLength(2).boost(10.0f))
//				.should(QueryBuilders.matchQuery("amenitySubCategory.stem", q).boost(10.0f));

//		

//		Facet specification
		Collection<AbstractAggregationBuilder<?>> allAggs = new ArrayList<AbstractAggregationBuilder<?>>();

		allAggs.add(
				AggregationBuilders.geoDistance("distance", new GeoPoint((top + bottom) / 2.0, (left + right) / 2.0))
						.field("centroid").unit(DistanceUnit.METERS).addUnboundedTo(300.0).addRange(300.0, 1000.0)
						.addRange(1000.0, 5000.0).addUnboundedFrom(5000));
		allAggs.add(AggregationBuilders.terms("amenitySubCategory").field("amenitySubCategory.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("openingHours").field("openingHours.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("wheelChair").field("wheelChair.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("internetAccess").field("internetAccess.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("delivery").field("delivery.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("driveThrough").field("driveThrough.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("paymentOptions").field("paymentOptions.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("cuisine").field("cuisine.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("seating").field("seating.keyword").size(100));
		allAggs.add(AggregationBuilders.terms("diet").field("diet.keyword").size(100));

		GeoDistanceSortBuilder sort = new GeoDistanceSortBuilder("centroid",
				new GeoPoint((top + bottom) / 2.0, (left + right) / 2.0));

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withAggregations(allAggs)
//				.withMaxResults(200)
//				.withFilter(QueryBuilders.termQuery("amenitySubcategory", "Pub"))
				.withSorts(sort).build();

		SearchHits<Place> placeHits = elasticsearchOperations.search(searchQuery, Place.class,
				IndexCoordinates.of("place"));

		AggregationsContainer<?> aggregationsContainer = placeHits.getAggregations();

		Aggregations aggregations = ((ElasticsearchAggregations) aggregationsContainer).aggregations();

		addAggregates("amenitySubCategory", aggregations, allAggregations);

		addAggregates("cuisine", aggregations, allAggregations);
		addAggregates("wheelChair", aggregations, allAggregations);
		addAggregates("seating", aggregations, allAggregations);
		addAggregates("openingHours", aggregations, allAggregations);

		addAggregates("internetAccess", aggregations, allAggregations);
		addAggregates("delivery", aggregations, allAggregations);
		addAggregates("driveThrough", aggregations, allAggregations);
		addAggregates("paymentOptions", aggregations, allAggregations);
		addAggregates("diet", aggregations, allAggregations);

		Range byDistance = aggregations.get("distance");
		for (Range.Bucket bucket : byDistance.getBuckets()) {
			allAggregations.add(new Aggregate("distance", bucket.getKeyAsString(), bucket.getDocCount(), 0L));
		}

		List<Place> placeMatches = new ArrayList<Place>();
		placeHits.forEach(searchHit -> {
			placeMatches.add(searchHit.getContent());
		});

		return new PlacesResponse(placeMatches, allAggregations);

	}

}
