package me.tsccoding.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoMain {
    public static void main(String args[]) {
        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");

        System.out.println("Database Connected");

        String username="zain";
        String incoming_id="abcd";
        String outgoing_id="wxyz";
        Document document = new Document ("username", username) ;
        document.append ("incomingID", incoming_id);
        document.append ("outgoingID", outgoing_id);

        collection.insertOne(document);
        // Document found = (Document) collection.find(new Document("username", "zain")).first();

       //f(found != null){
            //System.out.println("Found User");

            //Bson updatedvalue = new Document("Age", 26);
            //Bson updateoperation = new Document("$set", updatedvalue);
            //collection.updateOne(found,updateoperation);
            //System.out.println("User Updated");
        //}
    }
}
