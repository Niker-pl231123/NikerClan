package dev.nikee.managers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbManager {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;
    private String databaseName;

    public MongoDbManager(String url, String databaseName, String collectionName) {
        this.mongoClient = MongoClients.create(url);
        this.database = this.mongoClient.getDatabase(databaseName);
        this.databaseName = databaseName;
        this.collection = this.database.getCollection(collectionName);

        try {
            this.database.runCommand(new Document("ping", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoCollection<Document> getCollection() {
        return this.collection;
    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(databaseName);
    }


    public void closeConnection() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }

    }
}
