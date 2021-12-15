package com.arogyak.api.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.arogyak.api")
@ComponentScan(basePackages = { "com.arogyak.api" })
public class Config extends AbstractElasticsearchConfiguration {
	
	@Value("${pg2es.config.es_host}")
	private String esHost;
	
	@Value("${pg2es.config.es_port}")
	private String esPort;


	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {

		String connPath = esHost + ":" + esPort;

		final ClientConfiguration clientConfiguration = 
				ClientConfiguration
				.builder()
				.connectedTo("elastic:9200")
				.build();


		return RestClients
				.create(clientConfiguration)
				.rest();
	}
	

}
