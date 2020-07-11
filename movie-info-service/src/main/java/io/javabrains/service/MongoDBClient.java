package io.javabrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Service
public class MongoDBClient {

	@Value("${mongodb.dbname}")
	private String mongoDatabaseName;

	@Value("${mongodb.host}")
	private String mongodbHost;

	@Value("${spring.data.mongodb.uri}")
	private String mongoUrl;

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClient.class);

	public String getDatabasename() {
		return mongoDatabaseName;
	}

	public MongoDatabase getDatabase() {
		LOGGER.info("database name : " + mongoDatabaseName);
		return getMongoClient().getDatabase(mongoDatabaseName);
	}

	public MongoClient getMongoClient() {
		LOGGER.info("MONGODB-HOST : " + mongodbHost);
		LOGGER.info("mongoDBUrl : " + mongoUrl);

		MongoClientURI clientURI = new MongoClientURI(mongoUrl);
		MongoClient mongoClient = new MongoClient(clientURI);
		return mongoClient;
	}
}
