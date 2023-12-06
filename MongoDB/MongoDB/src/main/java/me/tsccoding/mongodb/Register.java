package me.tsccoding.mongodb;
import java.util.Random;
import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import java.util.logging.Level;
import org.bson.conversions.Bson;

public class Register {
    public static void main(String args[]) {

        String uri = "mongodb+srv://admin:admin@testcluster.yvs3ank.mongodb.net/?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("users");
        MongoCollection collection = mongoDatabase.getCollection("auth");
        
         java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        //Register
        Scanner input3 = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String username = input3.next();

        Scanner input4 = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String password = input4.next();

        // create a string of uppercase and lowercase characters and numbers
    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";

    // combine all strings
    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

    // create random string builder
    StringBuilder sb = new StringBuilder();

    // create an object of Random class
    Random random = new Random();

    // specify length of random string
    int length = 10;

    for(int i = 0; i < length; i++) {

      // generate random index number
      int index = random.nextInt(alphaNumeric.length());

      // get character specified by index
      // from the string
      char randomChar = alphaNumeric.charAt(index);

      // append the character to string builder
      sb.append(randomChar);
    }

    String randomString = sb.toString();

        Document founduser = (Document) collection.find(new Document("username", username)).first();
        Document foundid = (Document) collection.find(new Document("uniqueid", randomString)).first();
        if(founduser != null){
            
            System.out.println("Username already Exists!");
       }
       else{
        if(foundid == null){
        Document document = new Document ("username", username) ;
        document.append ("password", password);
        document.append ("uniqueid", randomString);
        collection.insertOne(document);
        System.out.println("User successfully created!");
       }
       else{
        System.out.println("ID already Exists!");
       }
    }
    }
            //Bson updatedvalue = new Document("Age", 26);
            //Bson updateoperation = new Document("$set", updatedvalue);
            //collection.updateOne(found,updateoperation);
            //System.out.println("User Updated");
}

/*package me.tsccoding.mongodb;
import java.util.Random;
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

        // create a string of uppercase and lowercase characters and numbers
    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";

    // combine all strings
    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

    // create random string builder
    StringBuilder sb = new StringBuilder();

    // create an object of Random class
    Random random = new Random();

    // specify length of random string
    int length = 10;

    for(int i = 0; i < length; i++) {

      // generate random index number
      int index = random.nextInt(alphaNumeric.length());

      // get character specified by index
      // from the string
      char randomChar = alphaNumeric.charAt(index);

      // append the character to string builder
      sb.append(randomChar);
    }

    String randomString = sb.toString();

        Document founduser = (Document) collection.find(new Document("username", username)).first();
        Document foundid = (Document) collection.find(new Document("uniqueid", randomString)).first();
        if(founduser != null){
            System.out.println("Username already Exists!");
       }
       else{
        if(foundid == null){
        Document document = new Document ("username", username) ;
        document.append ("password", password);
        document.append ("uniqueid", randomString);
        collection.insertOne(document);
        System.out.println("User successfully created!");
       }
       else{
        System.out.println("ID already Exists!");
       }
    }
    }
            //Bson updatedvalue = new Document("Age", 26);
            //Bson updateoperation = new Document("$set", updatedvalue);
            //collection.updateOne(found,updateoperation);
            //System.out.println("User Updated");
}
}  */