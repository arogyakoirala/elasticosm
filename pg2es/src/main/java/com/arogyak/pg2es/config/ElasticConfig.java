package com.arogyak.pg2es.config;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elastic search configuration class
 * 
 * @author arogyakoirala
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages 
        = "com.arogyak.pg2es.repository")
@ComponentScan(basePackages = { "com.arogyak.pg2es" })
public class ElasticConfig extends 
         AbstractElasticsearchConfiguration {
  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {

  final ClientConfiguration clientConfiguration = 
    ClientConfiguration
      .builder()
      .connectedTo("localhost:9200")
      .build();

  return RestClients.create(clientConfiguration).rest();
  }
}