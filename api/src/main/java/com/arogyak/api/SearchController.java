package com.arogyak.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arogyak.api.model.Place;

@RestController
@CrossOrigin(origins = "*")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping(value = "/search")
	public List<Place> search(@RequestParam String q) {
		return searchService.findPlace(q);
		
		
	}
	

}
