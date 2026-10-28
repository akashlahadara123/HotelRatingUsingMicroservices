package com.dn.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RatingServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServicesApplication.class, args);
	}

}
