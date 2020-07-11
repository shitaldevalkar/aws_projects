package io.javabrains.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

@Component
public class CustomAWSCredentialsProvider implements AWSCredentialsProvider {
	@Autowired
	private AWSCrdentialsConfig awsConfig;

	@Override
	public void refresh() {
	}

	@Override
	public AWSCredentials getCredentials() {
		return new AWSCredentials() {
			@Override
			public String getAWSSecretKey() {
				String key =  awsConfig.getAswSecretAccessKey();
				System.out.println("KEY : "+ key);
				return key;
			}

			@Override
			public String getAWSAccessKeyId() {
				String id = awsConfig.getAwsAccessKeyId();
				System.out.println("ID FROM CONFIG "+ id);
				return id;
			}
		};
	}
};
