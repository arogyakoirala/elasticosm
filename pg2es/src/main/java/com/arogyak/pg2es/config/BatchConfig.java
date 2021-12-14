package com.arogyak.pg2es.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arogyak.pg2es.model.NominatimPlace;
import com.arogyak.pg2es.tasklets.DeleteTasklet;


/**
 * Batch job configuration. This class is responsible for populating the Elastic
 * Search DB from the PostGRES based Nominatim Database on startup.
 * 
 * @author arogyakoirala
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DeleteTasklet deleteTasklet;

	/**
	 * @param dataSource
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		// this method shouldn't exist. Added because of
		// https://stackoverflow.com/questions/33249942/spring-batch-framework-auto-create-batch-table
	}

	/**
	 * Configure place import job.
	 * 
	 * @param jobBuilderFactory Job builder factory
	 * @param importPlaceStep   The batch step to be run
	 * @return {@link Job}
	 */

	@Bean
	public Job importJob(Step importPlaceStep) {

		return jobBuilderFactory.get("importJob").incrementer(new RunIdIncrementer()).start(importPlaceStep)
				.listener(importJobListener()).build();
	}

	/**
	 * @param stepBuilderFactory Step builder factory
	 * @param placeItemReader    The item reader that specifies how to read input
	 *                           from the Nominatim DB
	 * @param placeItemWriter    The item writer that specifies how to write output
	 *                           from the Nominatim DB
	 * @return {@link Step}
	 */
	@Bean
	public Step importPlaceStep(ItemReader<NominatimPlace> placeItemReader, ItemWriter<NominatimPlace> placeItemWriter) {
		return stepBuilderFactory.get("importPlaceStep").<NominatimPlace, NominatimPlace>chunk(1000).reader(placeItemReader)
				.writer(placeItemWriter).build();
	}

	/**
	 * Job Listener to create log file on successful execution.
	 * 
	 * @return
	 */
	@Bean
	public JobExecutionListener importJobListener() {

		JobExecutionListener listener = new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				System.out.println("Import job completed, now exiting.");
				System.exit(0);
			}
		};

		return listener;
	}

	@Bean
	public Job deleteJob() {
		return jobBuilderFactory.get("deleteJob").listener(deleteListener()).incrementer(new RunIdIncrementer())
				.flow(deleteStep()).end().build();
	}

	@Bean
	public Step deleteStep() {
		return stepBuilderFactory.get("deleteJobStep").tasklet(deleteTasklet).build();
	}

	private JobExecutionListener deleteListener() {
		return new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {

			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				System.out.println("Delete job completed, now importing.");
			}
		};
	}

}
