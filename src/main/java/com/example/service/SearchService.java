package com.example.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Map<String, Object>> searchAllCollections(String searchKey, String searchValue) {
        List<Map<String, Object>> searchResults = new ArrayList<>();

        // Get the database name (you can change this to match your setup)
        MongoDatabase database = mongoClient.getDatabase(mongoTemplate.getDb().getName());

        // Loop through all collections in the database
        for (String collectionName : database.listCollectionNames()) {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Perform search on each collection based on the key-value
            for (Document doc : collection.find(new Document(searchKey, searchValue))) {
                searchResults.add(doc);
            }
        }
        return searchResults;
    }
}
