package io.javabrains.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.javabrains.model.User;

@Service
public class DocumentDBUserService {

	@Value("${mongodb.users.table.name}")
	private String usersTableName;

	@Autowired
	private MongoDBClient mongodbClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentDBUserService.class);

	private Document createDocument(User user) {
		Document document = new Document();
		document.append("userId", user.getUserId());
		document.append("firstName", user.getFirstName());
		document.append("lastName", user.getLastName());
		document.append("email", user.getEmail());
		return document;
	}

	private User createUser(Document document) {
		int userID = document.getInteger("userId");
		String firstName = document.getString("firstName");
		String lastName = document.getString("lastName");
		String email = document.getString("email");
		return new User(userID, firstName, lastName, email);
	}

	public User addUser(User user) {
		LOGGER.info(" adduser() method");
		MongoDatabase database = mongodbClient.getDatabase();
		MongoCollection<Document> usersCollection = database.getCollection(usersTableName);
		LOGGER.info("USER ADDED");
		int topIndex = 0;
		List<User> userList = getUserList();
		for (User user1 : userList) {
			if (topIndex < user1.getUserId()) {
				topIndex = user1.getUserId();
			}
		}
		user.setUserId(topIndex + 1);

		Document document = createDocument(user);
		usersCollection.insertOne(document);
		LOGGER.info("USER ADDED");
		return user;
	}

	public List<User> getUserList() {
		LOGGER.info("in getUserList()");
		MongoDatabase database = mongodbClient.getDatabase();
		LOGGER.info("in getUserList() database " + database.getName());
		MongoCollection<Document> usersCollection = database.getCollection(usersTableName);
		LOGGER.info("in getUserList() collection " + usersCollection.getNamespace().getFullName());
		List<User> userList = new ArrayList<>();
		MongoCursor<Document> cursor = usersCollection.find().iterator();
		LOGGER.info("USER CURSOR CREATED");
		try {
			while (cursor.hasNext()) {
				LOGGER.info("Record Found ");
				Document document = cursor.next();
				LOGGER.info("Record Found Document  " + document.getString("email"));
				User user = createUser(document);
				LOGGER.info("USER DTO CREATED  " + user.getEmail());
				userList.add(user);

			}
		} finally {
			cursor.close();
		}

		Collections.sort(userList);
		return userList;
	}

	public int deleteUser(int id)  {
		MongoDatabase database = mongodbClient.getDatabase();
		MongoCollection<Document> usersCollection = database.getCollection(usersTableName);

		Document filter = new Document();
		filter.append("userId", id);
		usersCollection.deleteOne(filter);
		return id;
	}

	public User updateUser(User user, int id)  {
		MongoDatabase database = mongodbClient.getDatabase();
		MongoCollection<Document> usersCollection = database.getCollection(usersTableName);

		Document filter = new Document();
		filter.append("userId", id);
		Document document = createDocument(user);
		BasicDBObject setData = new BasicDBObject();
		setData.append("$set", document);

		usersCollection.updateOne(filter, setData);
		return user;
	}

}
