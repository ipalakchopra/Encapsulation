package me.tsccoding.mongodb;
import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.logging.Level;
import org.bson.conversions.Bson;

public class login {
    public static void main(String args[]) {

        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");
        
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        //Login
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String username = input1.next();

        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String password = input2.next();
        Document founduser = (Document) collection.find(new Document("username", username)).first();
        Document foundpass = (Document) collection.find(new Document("password", password)).first();

       if(founduser == null){
            System.out.println("User not found");
       }
       else if(founduser != null && foundpass == null){
        System.out.println("Inncorrect password! ");
       }
       else{
            System.out.println("Login seccess!");
       }
    }
} 