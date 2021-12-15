package com.arogyak.pg2es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//import com.arogyak.pg2es.$missing$;

/**
 * Elastic search configuration class
 * 
 * @author arogyakoirala
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.arogyak.pg2es.repository")
@ComponentScan(basePackages = { "com.arogyak.pg2es" })
public class ElasticConfig extends AbstractElasticsearchConfiguration {

	@Value("${pg2es.config.es_host}")
	private String esHost;
	
	@Value("${pg2es.config.es_port}")
	private String esPort;

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		
		String connPath = esHost + ":" + esPort;

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(connPath)
				.build();

		return RestClients.create(clientConfiguration).rest();
	}
}