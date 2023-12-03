package me.tsccoding.mongodb;
import java.util.Scanner;
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

        Scanner inputch = new Scanner(System.in);
        System.out.println("Login (1) or Register (2)");
        int choice = inputch.nextInt();

    if(choice == 1)
    {
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
        System.out.println("inncorrect pass");
       }
       else{
            System.out.println("Login seccess!");
       }
    }

    else{
        //Register
        Scanner input3 = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String username = input3.next();

        Scanner input4 = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String password = input4.next();

        Document founduser = (Document) collection.find(new Document("username", username)).first();

        if(founduser != null){
            System.out.println("Username already Exists!");
       }
       else{
        Document document = new Document ("username", username) ;
        document.append ("password", password);
        collection.insertOne(document);
        System.out.println("User successfully created!");
       }
    }
    }
            //Bson updatedvalue = new Document("Age", 26);
            //Bson updateoperation = new Document("$set", updatedvalue);
            //collection.updateOne(found,updateoperation);
            //System.out.println("User Updated");
}

