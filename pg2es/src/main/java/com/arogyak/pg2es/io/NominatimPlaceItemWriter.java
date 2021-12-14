package com.arogyak.pg2es.io;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arogyak.pg2es.model.NominatimPlace;
import com.arogyak.pg2es.repository.PlaceRepository;


/**
 * Write results from batch job into Elastic DB
 * 
 * @author arogyakoirala
 *
 */
@Component
public class NominatimPlaceItemWriter implements ItemWriter<NominatimPlace> {

	@Autowired
	private PlaceRepository placeRepository;

	/**
	 * @param items Chunk of places to write to Elastic DB
	 * @throws Exception
	 */
	@Override
	public void write(List<? extends NominatimPlace> items) throws Exception {
		try {
			placeRepository.saveAll(items);
		} catch (Exception e) {
			throw e;
		}
	}

}
