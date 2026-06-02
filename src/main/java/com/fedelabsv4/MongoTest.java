package com.fedelabsv4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoTest implements CommandLineRunner {

    private final MongoClient mongoClient;

    public MongoTest(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void run(String... args) {

        MongoDatabase db = mongoClient.getDatabase("fedelabsv4");

        db.runCommand(new org.bson.Document("ping", 1));

        System.out.println("✅ Conectado a MongoDB Atlas");
    }
}