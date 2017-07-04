package com.duleendra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by Duleendra on 16/6/17.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.duleendra.repository", repositoryImplementationPostfix = "CustomImpl")
public class ElasticsearchConfig {
}
