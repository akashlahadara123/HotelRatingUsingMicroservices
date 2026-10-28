package com.dn.tech.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import feign.Feign;

@Configuration
public class MyConfig {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
    	return new RestTemplate();
	}
}
