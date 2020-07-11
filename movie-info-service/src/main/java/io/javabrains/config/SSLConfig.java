package io.javabrains.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

@Configuration
public class SSLConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SSLConfig.class);

	@Value("${trust.store}")
	private String trustStore;

	@Value("${trust.store}")
	private Resource trustStoreResource;

	@Value("${trust.store.password}")
	private String trustStorePassword;

	public SSLConfig() {
		LOGGER.info("IN CONFIGURATION");
	}

	public void loadTrustFile() {
		try {
			setSSLTrstStoreProperties();
		} catch (IOException e) {
			LOGGER.error("IOException occured. ", e);
		}
	}

	private void setSSLTrstStoreProperties() throws IOException {

		LOGGER.info("TRUST STORE PASSWORD: " + trustStorePassword);

		String trustStoreFilePath = null;
		try {
			trustStoreFilePath = ResourceUtils.getURL(trustStore).getFile();
		} catch (FileNotFoundException ex) {
			LOGGER.error("Error while loading resource " + trustStore + " ", ex);
		}

		LOGGER.info("TRUST FILEPATH " + trustStoreFilePath);

		File file = new File(trustStoreFilePath);

		if (file.isFile() && file.canRead()) {
			LOGGER.info("trustStore: file exist " + file.getAbsolutePath());

		} else {
			LOGGER.info("trustStore: file not accessible, creating file in temp location");
			String trustFileName = trustStoreResource.getFilename();

			LOGGER.info("Trust FileName: " + trustFileName);
			File tempTrustStoreFile = createTrustStoreFileTempDir(trustFileName);
			trustStoreFilePath = tempTrustStoreFile.getAbsolutePath();
		}

		LOGGER.info("setting trust-store file path " + trustStoreFilePath);
		System.setProperty("javax.net.ssl.trustStore", trustStoreFilePath);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
	}

	@SuppressWarnings("deprecation")
	private File createTrustStoreFileTempDir(String filename) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(filename);

		InputStream inputStream = null;
		inputStream = classPathResource.getInputStream();

		File file = new File(FileUtils.getTempDirectory(), filename);
		try {
			if (file != null && file.exists()) {
				FileUtils.forceDelete(file);
			}
			FileUtils.copyInputStreamToFile(inputStream, file);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return file;
	}
}
