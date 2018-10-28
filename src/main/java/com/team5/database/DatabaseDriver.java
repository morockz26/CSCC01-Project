package com.team5.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONObject;

/**
 * Database driver class that deals with modifying database.
 */
public class DatabaseDriver {
	private MongoClientURI uri;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	/**
	 * Constructs the database driver.
	 * @param client The database client for connection.
	 * @param database The name of the database to get from the client.
	 */
	public DatabaseDriver(String uri, String database, String collection) {
		try {
			this.uri = new MongoClientURI(uri); // URI of the client
			this.client = new MongoClient(this.uri); // Connect to client
			this.database = this.client.getDatabase(database); // Get the database
			this.collection = this.database.getCollection(collection); // Get the collection
		} catch (MongoClientException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Accesses a collection in the database. (NOTE: if collection doesn't exist
	 * and you specify it, then inserting will create a new collection with the name
	 * specified).
	 * @param collection The collection to be accessed.
	 */
	public void useCollection(String collection) {
		this.collection = database.getCollection(collection);
	}
	
	/**
	 * Inserts JSON file object into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insert(JSONObject jsonObject) {
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON file objects into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertMany(List<JSONObject> jsonObjects) {
		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/*public static void main(String[] args) {
		DatabaseDriver db = new DatabaseDriver("mongodb://Mohammed:1234@localhost:27017/javatest", "javatest", "customers");
		JSONObject jsonObject = null;
		List<JSONObject> ob = new ArrayList<>();
		try {
			jsonObject = JSONLoader.parseJSONFile("testFiles/JSONTestFiles/testJSONFile_valid1.json");
			ob.add(jsonObject);
			jsonObject = JSONLoader.parseJSONFile("testFiles/JSONTestFiles/testJSONFile_valid2.json");
			ob.add(jsonObject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		db.insertMany(ob);
	}*/
}
