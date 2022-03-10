package ro.ase.acs;

import java.io.Closeable;
import java.io.IOException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ro.ase.acs.contracts.Operation;

public class NoSqlOperation implements Operation,Closeable {
	private MongoClient mongoClient;
	private MongoDatabase mongoDb;
	MongoCollection<Document> collection;
	
	public NoSqlOperation() {
		this.mongoClient = new MongoClient("localhost", 27017);
	    this.mongoDb = mongoClient.getDatabase("test");
	}

	@Override
	public void create() {
		if(this.mongoDb.getCollection("employees") != null) {
			this.mongoDb.getCollection("employees").drop();
		}
		
		this.mongoDb.createCollection("employees");
		
	}

	@Override
	public void insert() {
		Document employee1 = new Document().append("name", "Popescu Ion").append("address", "Bucharest").append("salary", 4000);
		
		this.collection = this. mongoDb.getCollection("employees");
		this.collection.insertOne(employee1);
		
		Document employee2 = new Document().append("name", "Ionescu Vasile").
				append("salary", 4500);
		this.collection.insertOne(employee2);
		
	}

	@Override
	public void read() {
		FindIterable<Document> result = this.collection.find();
		for(Document doc : result) {
			System.out.println(doc);
		}
		
	}

	@Override
	public void close() throws IOException {
		this.mongoClient.close();
		
	}

}
