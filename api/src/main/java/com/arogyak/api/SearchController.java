package com.arogyak.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arogyak.api.model.Bounds;
import com.arogyak.api.model.Place;
import com.arogyak.api.model.PlaceRequest;
import com.arogyak.api.model.PlacesResponse;

@RestController
@CrossOrigin(origins = "*")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping(value = "/search")
	public PlacesResponse search(@RequestParam String q, @RequestParam Double lat, @RequestParam Double lng, @RequestParam(required = false) Bounds bounds) {
		return searchService.findPlace(q, lat, lng, lat, lng);	
	}
	

	@GetMapping(value = "/search2")
	public PlacesResponse search2(@RequestParam String q, @RequestParam Double top, @RequestParam Double left, @RequestParam Double bottom, @RequestParam Double right) {
		return searchService.findPlace(q, top, left, bottom, right);	
	}
	
}
