package io.javabrains.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpsService {

	@Value("${trust.store}")
	private String trustStore;

	public String getHttsConnection() {
		System.out.println("BEFORE CALL ");
		try {
			System.setProperty("server.ssl.trustStore", "test");
			System.setProperty("server.ssl.trustStorePassword", "test");

			trustStore = "C:\\Program Files\\Java\\jre1.8.0_221\\lib\\security\\cacerts1";
			File filePath = new File(trustStore);
			String ksp = filePath.getAbsolutePath();
			System.out.println("FULL PATH " + filePath.getAbsolutePath());
			System.setProperty("Security.KeyStore.Location", ksp);
			System.setProperty("Security.KeyStore.Password", "Adinath234");

			String content = new RestTemplate()
					.getForObject("https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem", String.class);
			System.out.println("CONTENT " + content);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "failure " + ex.getMessage();
		}
		return "success";
	}
}
