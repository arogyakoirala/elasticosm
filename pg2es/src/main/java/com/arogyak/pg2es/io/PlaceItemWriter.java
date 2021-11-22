package com.arogyak.pg2es.io;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arogyak.pg2es.model.Place;
import com.arogyak.pg2es.repository.PlaceRepository;


/**
 * Write results from batch job into Elastic DB
 * 
 * @author arogyakoirala
 *
 */
@Component
public class PlaceItemWriter implements ItemWriter<Place> {

	@Autowired
	private PlaceRepository placeRepository;

	/**
	 * @param items Chunk of places to write to Elastic DB
	 * @throws Exception
	 */
	@Override
	public void write(List<? extends Place> items) throws Exception {
		try {
			for(Place p : items) {
				System.out.println(p.getName());
			}
			placeRepository.saveAll(items);
		} catch (Exception e) {
			throw e;
		}
	}

}
