package io.javabrains;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.javabrains.config.SSLConfig;

@EnableEurekaClient
@SpringBootApplication
public class MovieInfoServiceApplication {

	@Autowired
	private SSLConfig sslConfig;

	public static void main(String[] args) {
		SpringApplication.run(MovieInfoServiceApplication.class, args);
	}

	@PostConstruct
	public void postConstruct() {
		sslConfig.loadTrustFile();
	}
}
