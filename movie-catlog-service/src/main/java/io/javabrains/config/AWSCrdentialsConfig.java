package io.javabrains.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSCrdentialsConfig {

	@Value("${aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${aws.secret.access.key}")
	private String aswSecretAccessKey;
	
	@Value("${aws.access.region}")
	private String awsAccessRegion;
	
	public String getAwsAccessKeyId() {
		return awsAccessKeyId;
	}

	public String getAswSecretAccessKey() {
		return aswSecretAccessKey;
	}

	public String getAwsAccessRegion() {
		return awsAccessRegion;
	}
}
