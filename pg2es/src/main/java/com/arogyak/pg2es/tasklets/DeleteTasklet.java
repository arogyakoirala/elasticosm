package com.arogyak.pg2es.tasklets;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DeleteTasklet implements Tasklet {


//	@Autowired
//	private PlaceService placeService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

//		placeRepository.deleteAll();
//		placeService.deletePlaceIndex();
		return RepeatStatus.FINISHED;
	}

}
