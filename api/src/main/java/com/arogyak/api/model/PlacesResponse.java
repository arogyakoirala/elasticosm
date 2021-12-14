package com.arogyak.api.model;

import java.util.List;

public class PlacesResponse {

	private List<Place> results;
	private List<Aggregate> aggregations;

	public PlacesResponse(List<Place> results, List<Aggregate> aggregations) {
		super();
		this.results = results;
		this.aggregations = aggregations;
	}

	public List<Place> getResults() {
		return results;
	}

	public void setResults(List<Place> results) {
		this.results = results;
	}

	public List<Aggregate> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<Aggregate> aggregations) {
		this.aggregations = aggregations;
	}

}
